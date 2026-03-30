package com.self.payment_service.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemResponseDto {
    private Long productIdentifier;
    private Integer productQuantity;
    private Double productPrice;
    private Double productSubtotal;
}
