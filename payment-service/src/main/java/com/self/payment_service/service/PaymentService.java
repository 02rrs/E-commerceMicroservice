package com.self.payment_service.service;

import com.self.payment_service.dto.PaymentRequestDTO;
import com.self.payment_service.dto.PaymentResponseDTO;
import com.self.payment_service.dto.PaymentVerificationDTO;
import com.self.payment_service.util.ResponseStructure;

public interface PaymentService {

    ResponseStructure<PaymentResponseDTO> initiatePayment(
            PaymentRequestDTO paymentRequestDTO, String token
    );

    ResponseStructure<PaymentResponseDTO> verifyPayment(
            PaymentVerificationDTO paymentVerificationDTO, String token
    );
}
