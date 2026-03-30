package com.self.order_service.mapper;

import com.self.order_service.dto.OrderItemRequestDTO;
import com.self.order_service.dto.OrderItemResponseDTO;
import com.self.order_service.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    // DTO → Entity
    @Mapping(source = "productIdentifier", target = "productId")
    @Mapping(source = "productQuantity", target = "quantity")
    @Mapping(target = "price", ignore = true)
    @Mapping(target = "subtotal", ignore = true)
    @Mapping(target = "order", ignore = true)
    OrderItem toEntity(OrderItemRequestDTO dto);


    // Entity → DTO
    @Mapping(source = "productId", target = "productIdentifier")
    @Mapping(source = "quantity", target = "productQuantity")
    @Mapping(source = "price", target = "productPrice")
    @Mapping(source = "subtotal", target = "productSubtotal")
    OrderItemResponseDTO toResponseDTO(OrderItem orderItem);
}
