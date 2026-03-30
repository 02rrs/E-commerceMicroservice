package com.self.payment_service.dto;

import com.self.payment_service.enums.PaymentMethod;
import com.self.payment_service.enums.PaymentStatus;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponseDTO {

    private String paymentIdentifier;

    private String orderIdentifier;

    private Double paymentAmount;

    private PaymentMethod paymentMode;

    private PaymentStatus paymentStatus;

    private LocalDateTime paymentTimestamp;
}
