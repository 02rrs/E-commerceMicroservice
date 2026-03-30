package com.self.payment_service.controller;

import com.self.payment_service.dto.PaymentRequestDTO;
import com.self.payment_service.dto.PaymentResponseDTO;
import com.self.payment_service.dto.PaymentVerificationDTO;
import com.self.payment_service.service.PaymentService;
import com.self.payment_service.util.ResponseStructure;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;


    // Initiate Payment
    @PostMapping("/initiate")
    public ResponseEntity<ResponseStructure<PaymentResponseDTO>> initiatePayment(
            @Valid @RequestBody PaymentRequestDTO paymentRequestDTO, HttpServletRequest request) {

        String token=request.getHeader("Authorization");

        ResponseStructure<PaymentResponseDTO> response =
                paymentService.initiatePayment(paymentRequestDTO, token);

        return ResponseEntity
                .status(response.getStatusCode())
                .body(response);
    }


    // Verify Payment
    @PostMapping("/verify")
    public ResponseEntity<ResponseStructure<PaymentResponseDTO>> verifyPayment(
            @Valid @RequestBody PaymentVerificationDTO paymentVerificationDTO, HttpServletRequest request) {

        String token=request.getHeader("Authorization");

        ResponseStructure<PaymentResponseDTO> response =
                paymentService.verifyPayment(paymentVerificationDTO, token);

        return ResponseEntity
                .status(response.getStatusCode())
                .body(response);
    }
}
