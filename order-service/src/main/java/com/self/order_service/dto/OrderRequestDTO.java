package com.self.order_service.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequestDTO {

    private Long customerId;
    private List<OrderItemRequestDTO> products;
}
