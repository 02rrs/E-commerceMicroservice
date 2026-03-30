package com.self.product_service.repository;

import com.self.product_service.entity.Product;
import com.self.product_service.enums.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    List<Product> findByProductTitleContainingIgnoreCase(String name);

    List<Product> findByProductPriceBetween(Double min, Double max);

    List<Product> findByCategory(ProductCategory category);
}
