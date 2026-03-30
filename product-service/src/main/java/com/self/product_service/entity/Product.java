package com.self.product_service.entity;

import com.self.product_service.enums.ProductCategory;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productTitle;

    private String productDescription;

    private Double productPrice;

    private Integer quantityInStock;

    @Enumerated(EnumType.STRING)
    private ProductCategory category;
}
