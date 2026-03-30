package com.self.product_service.service;

import com.self.product_service.dto.ProductRequestDTO;
import com.self.product_service.dto.ProductResponseDTO;
import com.self.product_service.enums.ProductCategory;
import com.self.product_service.util.ResponseStructure;

import java.util.List;

public interface ProductService {

    ResponseStructure<ProductResponseDTO> addProduct(ProductRequestDTO requestDTO);

    ResponseStructure<ProductResponseDTO> updateProduct(Long productId, ProductRequestDTO requestDTO);

    ResponseStructure<String> deleteProduct(Long productId);

    ResponseStructure<ProductResponseDTO> getProductById(Long productId);

    ResponseStructure<List<ProductResponseDTO>> getAllProducts();

    ResponseStructure<List<ProductResponseDTO>> searchProducts(
            String name,
            Double minPrice,
            Double maxPrice,
            ProductCategory category,
            int page,
            int size,
            String sortBy,
            String direction
    );

    ResponseStructure<ProductResponseDTO> deductStock(Long productId, Integer quantity);
}
