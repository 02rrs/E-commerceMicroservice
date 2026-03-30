package com.self.user_service.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.self.user_service.util.ResponseStructure;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    // SKIP FILTER FOR PUBLIC ENDPOINTS
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {

        String path = request.getRequestURI();

        // MOST IMPORTANT FIX (use contains instead of startsWith)
        return path.contains("/swagger-ui")
                || path.contains("/api-docs")
                || path.contains("/swagger-resources")
                || path.contains("/webjars")
                || path.contains("/favicon.ico")
                || path.contains("/api/users/register")
                || path.contains("/api/users/login");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {

            ResponseStructure<String> error =
                    ResponseStructure.build(
                            HttpStatus.UNAUTHORIZED,
                            "Unauthorized",
                            "Please login first"
                    );

            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");

            new ObjectMapper().writeValue(response.getWriter(), error);
            return;
        }

        String token = authHeader.substring(7);

        try {
            Claims claims = jwtUtil.validateToken(token);

            String email = claims.getSubject();
            String role = claims.get("role", String.class);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            email,
                            null,
                            List.of(new SimpleGrantedAuthority("ROLE_" + role))
                    );

            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {

            ResponseStructure<String> error =
                    ResponseStructure.build(
                            HttpStatus.UNAUTHORIZED,
                            "Invalid Token",
                            "Authentication failed"
                    );

            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");

            new ObjectMapper().writeValue(response.getWriter(), error);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
