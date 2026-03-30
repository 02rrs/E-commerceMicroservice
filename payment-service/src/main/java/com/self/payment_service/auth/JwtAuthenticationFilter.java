package com.self.payment_service.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.self.payment_service.util.ResponseStructure;
import com.self.payment_service.util.ResponseUtil;
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

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();

        if (path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs")
                || path.startsWith("/api-docs")) {

            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")){

            ResponseStructure<String> error =
                    ResponseUtil.buildResponse(
                            HttpStatus.UNAUTHORIZED.value(),
                            "Unauthorized",
                            "Please login to access this resource"
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
                    ResponseUtil.buildResponse(
                            HttpStatus.UNAUTHORIZED.value(),
                            "Invalid Token",
                            "Authentication failed"
                    );

            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");

            new ObjectMapper().writeValue(response.getWriter(), error);
            return;
        }

        filterChain.doFilter(request,response);
    }
}
