package com.self.product_service.mapper;

import com.self.product_service.dto.ProductRequestDTO;
import com.self.product_service.dto.ProductResponseDTO;
import com.self.product_service.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "productName", target = "productTitle")
    @Mapping(source = "description", target = "productDescription")
    @Mapping(source = "price", target = "productPrice")
    @Mapping(source = "stockQuantity", target = "quantityInStock")
    Product toEntity(ProductRequestDTO dto);


    @Mapping(source = "id", target = "productId")
    @Mapping(source = "productTitle", target = "productName")
    @Mapping(source = "productDescription", target = "description")
    @Mapping(source = "productPrice", target = "price")
    @Mapping(source = "quantityInStock", target = "stockQuantity")
    ProductResponseDTO toDTO(Product product);
}
