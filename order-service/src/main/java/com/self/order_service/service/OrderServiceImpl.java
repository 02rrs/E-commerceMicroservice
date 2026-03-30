package com.self.order_service.service;

import com.self.order_service.client.ProductServiceClient;
import com.self.order_service.client.UserServiceClient;
import com.self.order_service.dto.OrderItemRequestDTO;
import com.self.order_service.dto.OrderRequestDTO;
import com.self.order_service.dto.OrderResponseDTO;
import com.self.order_service.dto.ProductResponseDTO;
import com.self.order_service.entity.Order;
import com.self.order_service.entity.OrderItem;
import com.self.order_service.enums.OrderStatus;
import com.self.order_service.exception.OrderNotFoundException;
import com.self.order_service.mapper.OrderItemMapper;
import com.self.order_service.mapper.OrderMapper;
import com.self.order_service.repository.OrderRepository;
import com.self.order_service.util.ResponseStructure;
import com.self.order_service.util.ResponseUtil;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    private final UserServiceClient userServiceClient;
    private final ProductServiceClient productServiceClient;

    /**
     * Circuit Breaker protects calls to USER-SERVICE and PRODUCT-SERVICE
     */
    @Override
    @CircuitBreaker(name = "orderService", fallbackMethod = "placeOrderFallback")
    public ResponseStructure<OrderResponseDTO> placeOrder(
            OrderRequestDTO orderRequestDTO,
            HttpServletRequest request) {

        // Extract JWT Token
        String token = request.getHeader("Authorization");

        System.out.println("TOKEN IN ORDER SERVICE: " + token);

        // Validate User
        userServiceClient.validateUser(orderRequestDTO.getCustomerId(), token);

        // Convert DTO → Entity
        Order order = orderMapper.toEntity(orderRequestDTO);

        order.setOrderUuid("ORD-" + UUID.randomUUID());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.CREATED);

        double totalAmount = 0.0;
        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemRequestDTO itemDTO : orderRequestDTO.getProducts()) {

            // Fetch product from Product Service
            ProductResponseDTO product =
                    productServiceClient.getProduct(
                            itemDTO.getProductIdentifier(),
                            token
                    );

            // Check stock
            if (product.getStockQuantity() < itemDTO.getProductQuantity()) {
                throw new RuntimeException(
                        "Insufficient stock for product " + product.getProductName()
                );
            }

            // Deduct stock
            productServiceClient.deductStock(
                    itemDTO.getProductIdentifier(),
                    itemDTO.getProductQuantity(),
                    token
            );

            // Calculate subtotal
            double subtotal = product.getPrice() * itemDTO.getProductQuantity();

            totalAmount += subtotal;

            // Create OrderItem
            OrderItem orderItem = orderItemMapper.toEntity(itemDTO);

            orderItem.setPrice(product.getPrice());
            orderItem.setSubtotal(subtotal);
            orderItem.setOrder(order);

            orderItems.add(orderItem);
        }

        order.setItems(orderItems);
        order.setTotalAmount(totalAmount);

        // Save Order
        Order savedOrder = orderRepository.save(order);

        OrderResponseDTO responseDTO = orderMapper.toResponseDTO(savedOrder);

        return ResponseUtil.buildResponse(
                201,
                "Order placed successfully",
                responseDTO
        );
    }

    /**
     * FALLBACK METHOD
     */
    public ResponseStructure<OrderResponseDTO> placeOrderFallback(
            OrderRequestDTO orderRequestDTO,
            HttpServletRequest request,
            Throwable throwable) {

        return ResponseUtil.buildResponse(
                503,
                "Order service temporarily unavailable. Please try again later.",
                null
        );
    }

    @Override
    public ResponseStructure<OrderResponseDTO> getOrderByUuid(String orderUuid) {

        Order order = orderRepository.findByOrderUuid(orderUuid);

        if (order == null) {
            throw new OrderNotFoundException("Order with UUID " + orderUuid + " not found");
        }

        OrderResponseDTO responseDTO = orderMapper.toResponseDTO(order);

        return ResponseUtil.buildResponse(
                200,
                "Order fetched successfully",
                responseDTO
        );
    }

    @Override
    public ResponseStructure<Page<OrderResponseDTO>> getUserOrders(Long userId, int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("orderDate").descending());

        Page<Order> orders = orderRepository.findByUserId(userId, pageable);

        Page<OrderResponseDTO> responsePage =
                orders.map(orderMapper::toResponseDTO);

        return ResponseUtil.buildResponse(
                200,
                "User orders fetched successfully",
                responsePage
        );
    }

    @Override
    public ResponseStructure<OrderResponseDTO> updateOrderStatus(String orderUuid, String status) {

        Order order = orderRepository.findByOrderUuid(orderUuid);

        if (order == null) {
            throw new OrderNotFoundException("Order with UUID " + orderUuid + " not found");
        }

        order.setStatus(OrderStatus.valueOf(status));

        orderRepository.save(order);

        OrderResponseDTO responseDTO = orderMapper.toResponseDTO(order);

        return ResponseUtil.buildResponse(
                200,
                "Order status updated successfully",
                responseDTO
        );
    }
}
