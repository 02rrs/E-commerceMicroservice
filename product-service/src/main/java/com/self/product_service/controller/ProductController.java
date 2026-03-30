package com.self.product_service.controller;

import com.self.product_service.dto.ProductRequestDTO;
import com.self.product_service.dto.ProductResponseDTO;
import com.self.product_service.enums.ProductCategory;
import com.self.product_service.service.ProductService;
import com.self.product_service.util.ResponseStructure;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // CREATE PRODUCT
    @PostMapping
    public ResponseStructure<ProductResponseDTO> addProduct(
            @RequestBody ProductRequestDTO requestDTO) {

        return productService.addProduct(requestDTO);
    }


    // UPDATE PRODUCT
    @PutMapping("/{productId}")
    public ResponseStructure<ProductResponseDTO> updateProduct(
            @PathVariable Long productId,
            @RequestBody ProductRequestDTO requestDTO) {

        return productService.updateProduct(productId, requestDTO);
    }


    // DELETE PRODUCT
    @DeleteMapping("/{productId}")
    public ResponseStructure<String> deleteProduct(
            @PathVariable Long productId) {

        return productService.deleteProduct(productId);
    }


    // GET PRODUCT BY ID
    @GetMapping("/{productId}")
    public ResponseStructure<ProductResponseDTO> getProductById(
            @PathVariable Long productId) {

        return productService.getProductById(productId);
    }


    // GET ALL PRODUCTS
    @GetMapping
    public ResponseStructure<List<ProductResponseDTO>> getAllProducts() {

        return productService.getAllProducts();
    }


    // SEARCH / FILTER PRODUCTS
    @GetMapping("/search")
    public ResponseStructure<List<ProductResponseDTO>> searchProducts(

            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) ProductCategory category,

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,

            @RequestParam(defaultValue = "productPrice") String sortBy,
            @RequestParam(defaultValue = "asc") String direction

    ) {
        return productService.searchProducts(name, minPrice, maxPrice, category, page, size, sortBy, direction);
    }

    @PutMapping("/{productId}/deduct-stock")
    public ResponseStructure<ProductResponseDTO> deductStock(
            @PathVariable Long productId,
            @RequestParam Integer quantity) {

        return productService.deductStock(productId, quantity);
    }
}
