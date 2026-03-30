package com.self.order_service.dto;

import com.self.order_service.enums.ProductCategory;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseDTO {
    private Long productId;

    private String productName;

    private String description;

    private Double price;

    private Integer stockQuantity;

    private ProductCategory category;
}
