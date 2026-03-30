package com.self.product_service.service;

import com.self.product_service.dto.ProductRequestDTO;
import com.self.product_service.dto.ProductResponseDTO;
import com.self.product_service.entity.Product;
import com.self.product_service.enums.ProductCategory;
import com.self.product_service.exception.InsufficientStockException;
import com.self.product_service.exception.ProductNotFoundException;
import com.self.product_service.mapper.ProductMapper;
import com.self.product_service.repository.ProductRepository;
import com.self.product_service.util.ProductSpecification;
import com.self.product_service.util.ResponseStructure;
import com.self.product_service.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ResponseStructure<ProductResponseDTO> addProduct(ProductRequestDTO requestDTO) {

        Product product = productMapper.toEntity(requestDTO);

        Product savedProduct = productRepository.save(product);

        ProductResponseDTO responseDTO = productMapper.toDTO(savedProduct);

        return ResponseUtil.buildResponse(
                201,
                "Product created successfully",
                responseDTO
        );
    }

    @Override
    @CachePut(value = "product", keyGenerator = "customKeyGenerator")
    public ResponseStructure<ProductResponseDTO> updateProduct(Long productId, ProductRequestDTO requestDTO) {

        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product with id " + productId + " not found"));

        existingProduct.setProductTitle(requestDTO.getProductName());
        existingProduct.setProductDescription(requestDTO.getDescription());
        existingProduct.setProductPrice(requestDTO.getPrice());
        existingProduct.setQuantityInStock(requestDTO.getStockQuantity());
        existingProduct.setCategory(requestDTO.getCategory());

        Product updatedProduct = productRepository.save(existingProduct);

        ProductResponseDTO responseDTO = productMapper.toDTO(updatedProduct);

        return ResponseUtil.buildResponse(
                200,
                "Product updated successfully",
                responseDTO
        );
    }

    @Override
    @CacheEvict(value = "product", keyGenerator = "customKeyGenerator")
    public ResponseStructure<String> deleteProduct(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product with id " + productId + " not found"));

        productRepository.delete(product);

        return ResponseUtil.buildResponse(
                200,
                "Product deleted successfully",
                "Deleted product id: " + productId
        );
    }


    @Override
    @Cacheable(value = "product", keyGenerator = "customKeyGenerator")
    public ResponseStructure<ProductResponseDTO> getProductById(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product with id " + productId + " not found"));

        ProductResponseDTO responseDTO = productMapper.toDTO(product);

        return ResponseUtil.buildResponse(
                200,
                "Product fetched successfully",
                responseDTO
        );
    }

    @Override
    public ResponseStructure<List<ProductResponseDTO>> getAllProducts() {

        List<ProductResponseDTO> products = productRepository.findAll()
                .stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseUtil.buildResponse(
                200,
                "All products fetched successfully",
                products
        );
    }

    @Override
    @Cacheable(value = "product-search", keyGenerator = "customKeyGenerator")
    public ResponseStructure<List<ProductResponseDTO>> searchProducts(
            String name,
            Double minPrice,
            Double maxPrice,
            ProductCategory category,
            int page,
            int size,
            String sortBy,
            String direction
    ) {

        Sort sort = direction.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Product> productPage = productRepository.findAll(
                ProductSpecification.searchProducts(name, minPrice, maxPrice, category),
                pageable
        );

        List<ProductResponseDTO> responseDTOList = productPage.getContent()
                .stream()
                .map(productMapper::toDTO)
                .toList();

        return ResponseUtil.buildResponse(
                200,
                "Products fetched successfully",
                responseDTOList
        );
    }

    @Override
    public ResponseStructure<ProductResponseDTO> deductStock(Long productId, Integer quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        if (product.getQuantityInStock() < quantity) {
            throw new InsufficientStockException("Not enough stock available");
        }

        product.setQuantityInStock(product.getQuantityInStock() - quantity);

        productRepository.save(product);

        ProductResponseDTO responseDTO = productMapper.toDTO(product);

        return ResponseUtil.buildResponse(
                200,
                "Stock updated successfully",
                responseDTO
        );
    }
}
