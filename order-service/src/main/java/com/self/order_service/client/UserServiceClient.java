package com.self.order_service.client;

import com.self.order_service.exception.UserNotFoundException;
import com.self.order_service.util.ResponseStructure;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class UserServiceClient {

    private final RestTemplate restTemplate;

    public void validateUser(Long userId, String token) {

        String url = "http://USER-SERVICE/api/users/" + userId;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<ResponseStructure> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        entity,
                        ResponseStructure.class
                );

        if (response.getBody() == null || response.getBody().getData() == null) {
            throw new UserNotFoundException("User with ID " + userId + " not found");
        }
    }
}
