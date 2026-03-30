package com.self.product_service.util;

import com.self.product_service.entity.Product;
import com.self.product_service.enums.ProductCategory;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {

    public static Specification<Product> searchProducts(
            String name,
            Double minPrice,
            Double maxPrice,
            ProductCategory category) {

        return (root, query, criteriaBuilder) -> {

            var predicates = criteriaBuilder.conjunction();

            if (name != null && !name.isBlank()) {
                predicates.getExpressions().add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("productTitle")),
                                "%" + name.toLowerCase() + "%"
                        )
                );
            }

            if (minPrice != null && maxPrice != null) {
                predicates.getExpressions().add(
                        criteriaBuilder.between(
                                root.get("productPrice"),
                                minPrice,
                                maxPrice
                        )
                );
            }

            if (category != null) {
                predicates.getExpressions().add(
                        criteriaBuilder.equal(
                                root.get("category"),
                                category
                        )
                );
            }

            return predicates;
        };
    }
}
