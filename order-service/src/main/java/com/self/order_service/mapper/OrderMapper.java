package com.self.order_service.mapper;

import com.self.order_service.dto.OrderRequestDTO;
import com.self.order_service.dto.OrderResponseDTO;
import com.self.order_service.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = OrderItemMapper.class)
public interface OrderMapper {

    // DTO → Entity
    @Mapping(source = "customerId", target = "userId")
    @Mapping(target = "orderUuid", ignore = true)
    @Mapping(target = "orderDate", ignore = true)
    @Mapping(target = "totalAmount", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "items", ignore = true)
    Order toEntity(OrderRequestDTO dto);


    // Entity → DTO
    @Mapping(source = "orderUuid", target = "orderReference")
    @Mapping(source = "userId", target = "customerId")
    @Mapping(source = "totalAmount", target = "orderAmount")
    @Mapping(source = "orderDate", target = "orderPlacedAt")
    @Mapping(source = "status", target = "orderStatus")
    @Mapping(source = "items", target = "products")
    OrderResponseDTO toResponseDTO(Order order);
}
