package com.self.product_service.dto;

import com.self.product_service.enums.ProductCategory;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseDTO implements Serializable {

    private Long productId;

    private String productName;

    private String description;

    private Double price;

    private Integer stockQuantity;

    private ProductCategory category;
}
