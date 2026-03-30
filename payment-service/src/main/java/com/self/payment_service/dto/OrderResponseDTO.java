package com.self.payment_service.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDTO {

    private String orderReference;
    private Long customerId;
    private Double orderAmount;
    private LocalDateTime orderPlacedAt;
    private String orderStatus;
    private List<OrderItemResponseDto> products;
}
