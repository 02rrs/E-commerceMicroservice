package com.self.payment_service.service;

import com.self.payment_service.client.OrderServiceClient;
import com.self.payment_service.dto.PaymentRequestDTO;
import com.self.payment_service.dto.PaymentResponseDTO;
import com.self.payment_service.dto.PaymentVerificationDTO;
import com.self.payment_service.entity.Payment;
import com.self.payment_service.enums.PaymentStatus;
import com.self.payment_service.exception.InvalidPaymentRequestException;
import com.self.payment_service.exception.PaymentAlreadyProcessedException;
import com.self.payment_service.exception.PaymentNotFoundException;
import com.self.payment_service.mapper.PaymentMapper;
import com.self.payment_service.repository.PaymentRepository;
import com.self.payment_service.util.ResponseStructure;
import com.self.payment_service.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService{

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final OrderServiceClient orderServiceClient;

    private final Random random = new Random();


    @Override
    public ResponseStructure<PaymentResponseDTO> initiatePayment(PaymentRequestDTO paymentRequestDTO, String token) {

        // Prevent duplicate payment for same order
        paymentRepository.findByOrderUuid(paymentRequestDTO.getOrderIdentifier())
                .ifPresent(p -> {
                    throw new InvalidPaymentRequestException(
                            "Payment already exists for order: " + paymentRequestDTO.getOrderIdentifier());
                });

        // Fetch order details from Order Service
        var order = orderServiceClient.getOrder(
                paymentRequestDTO.getOrderIdentifier(),
                token
        );

        // Convert DTO → Entity
        Payment payment = paymentMapper.toEntity(paymentRequestDTO);

        payment.setPaymentUuid(UUID.randomUUID().toString());
        payment.setPaymentDate(LocalDateTime.now());
        payment.setStatus(PaymentStatus.INITIATED);

        // Amount comes from Order Service
        payment.setAmount(order.getOrderAmount());
        payment.setOrderUuid(order.getOrderReference());

        paymentRepository.save(payment);

        PaymentResponseDTO responseDTO = paymentMapper.toResponseDTO(payment);

        return ResponseUtil.buildResponse(
                HttpStatus.CREATED.value(),
                "Payment initiated for amount ₹" + payment.getAmount(),
                responseDTO
        );
    }

    @Override
    public ResponseStructure<PaymentResponseDTO> verifyPayment(PaymentVerificationDTO paymentVerificationDTO, String token) {

        Payment payment = paymentRepository
                .findByPaymentUuid(paymentVerificationDTO.getPaymentIdentifier())
                .orElseThrow(() -> new PaymentNotFoundException(
                        "Payment not found for UUID: " + paymentVerificationDTO.getPaymentIdentifier()));

        // Prevent verifying same payment twice
        if (payment.getStatus() == PaymentStatus.SUCCESS) {
            throw new PaymentAlreadyProcessedException(
                    "Payment already completed for paymentUuid: " + payment.getPaymentUuid());
        }

        boolean paymentSuccess = random.nextBoolean();

        if (paymentSuccess) {

            payment.setStatus(PaymentStatus.SUCCESS);

            // Update order status in Order Service
            orderServiceClient.updateOrderStatus(
                    payment.getOrderUuid(),
                    token
            );

        } else {

            payment.setStatus(PaymentStatus.FAILED);
        }

        paymentRepository.save(payment);

        PaymentResponseDTO responseDTO = paymentMapper.toResponseDTO(payment);

        return ResponseUtil.buildResponse(
                HttpStatus.OK.value(),
                "Payment of ₹" + payment.getAmount() + " " + payment.getStatus(),
                responseDTO
        );
    }
}
