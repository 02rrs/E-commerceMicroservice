package com.self.order_service.service;

import com.self.order_service.dto.OrderRequestDTO;
import com.self.order_service.dto.OrderResponseDTO;
import com.self.order_service.util.ResponseStructure;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;

public interface OrderService {

    ResponseStructure<OrderResponseDTO> placeOrder(
            OrderRequestDTO orderRequestDTO,
            HttpServletRequest request);

    ResponseStructure<OrderResponseDTO> getOrderByUuid(String orderUuid);

    ResponseStructure<Page<OrderResponseDTO>> getUserOrders(
            Long userId,
            int page,
            int size
    );

    ResponseStructure<OrderResponseDTO> updateOrderStatus(String orderUuid, String status);
}
