package com.self.order_service.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemResponseDTO {
    private Long productIdentifier;
    private Integer productQuantity;
    private Double productPrice;
    private Double productSubtotal;

}
