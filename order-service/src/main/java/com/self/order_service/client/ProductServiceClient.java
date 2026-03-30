package com.self.order_service.client;

import com.self.order_service.dto.ProductResponseDTO;
import com.self.order_service.exception.ProductNotFoundException;
import com.self.order_service.util.ResponseStructure;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class ProductServiceClient {

    private final RestTemplate restTemplate;

    public ProductResponseDTO getProduct(Long productId, String token) {

        String url = "http://PRODUCT-SERVICE/products/" + productId;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<ResponseStructure<ProductResponseDTO>> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        entity,
                        new ParameterizedTypeReference<ResponseStructure<ProductResponseDTO>>() {}
                );

        ResponseStructure<ProductResponseDTO> body = response.getBody();

        if (body == null || body.getData() == null) {
            throw new ProductNotFoundException("Product with ID " + productId + " not found");
        }

        return body.getData();
    }


    public void deductStock(Long productId, Integer quantity, String token) {

        String url =
                "http://PRODUCT-SERVICE/products/"
                        + productId
                        + "/deduct-stock?quantity="
                        + quantity;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        restTemplate.exchange(
                url,
                HttpMethod.PUT,
                entity,
                Void.class
        );
    }
}
