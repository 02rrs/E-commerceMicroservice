package com.self.payment_service.dto;

import com.self.payment_service.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequestDTO {

    @NotNull(message = "Order identifier cannot be null")
    private String orderIdentifier;


    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMode;
}
