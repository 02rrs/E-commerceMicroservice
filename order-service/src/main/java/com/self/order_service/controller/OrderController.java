package com.self.order_service.controller;

import com.self.order_service.dto.OrderRequestDTO;
import com.self.order_service.dto.OrderResponseDTO;
import com.self.order_service.service.OrderService;
import com.self.order_service.util.ResponseStructure;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;


    // Place Order
    @PostMapping
    public ResponseEntity<ResponseStructure<OrderResponseDTO>> placeOrder(
            @RequestBody OrderRequestDTO orderRequestDTO,
            HttpServletRequest request) {

        ResponseStructure<OrderResponseDTO> response =
                orderService.placeOrder(orderRequestDTO, request);

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


    // Get Order by UUID
    @GetMapping("/{orderUuid}")
    public ResponseEntity<ResponseStructure<OrderResponseDTO>> getOrderByUuid(
            @PathVariable String orderUuid) {

        ResponseStructure<OrderResponseDTO> response =
                orderService.getOrderByUuid(orderUuid);

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


    // Get User Orders with Pagination
    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseStructure<Page<OrderResponseDTO>>> getUserOrders(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        ResponseStructure<Page<OrderResponseDTO>> response =
                orderService.getUserOrders(userId, page, size);

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PatchMapping("/{orderUuid}/status")
    public ResponseEntity<ResponseStructure<OrderResponseDTO>> updateOrderStatus(
            @PathVariable String orderUuid,
            @RequestParam String status) {

        ResponseStructure<OrderResponseDTO> response =
                orderService.updateOrderStatus(orderUuid, status);

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
