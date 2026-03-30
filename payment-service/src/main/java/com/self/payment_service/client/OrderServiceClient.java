package com.self.payment_service.client;

import com.self.payment_service.dto.OrderResponseDTO;
import com.self.payment_service.exception.OrderServiceUnavailableException;
import com.self.payment_service.util.ResponseStructure;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class OrderServiceClient {

    private final RestTemplate restTemplate;

    private static final String ORDER_SERVICE_BASE_URL = "http://ORDER-SERVICE/orders/";

    @CircuitBreaker(name = "orderService", fallbackMethod = "orderServiceFallback")
    public void updateOrderStatus(String orderUuid, String token) {

        String url = ORDER_SERVICE_BASE_URL + orderUuid + "/status?status=COMPLETED";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        restTemplate.exchange(
                url,
                HttpMethod.PATCH,
                entity,
                Void.class
        );
    }

    public void orderServiceFallback(String orderUuid, String token, Throwable throwable) {

        throw new OrderServiceUnavailableException(
                "Order Service is currently unavailable. Unable to update order status for orderUuid: "
                        + orderUuid
        );
    }

    public OrderResponseDTO getOrder(String orderUuid, String token) {

        String url = ORDER_SERVICE_BASE_URL + orderUuid;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<ResponseStructure<OrderResponseDTO>> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        entity,
                        new ParameterizedTypeReference<ResponseStructure<OrderResponseDTO>>() {}
                );

        return response.getBody().getData();
    }
}
