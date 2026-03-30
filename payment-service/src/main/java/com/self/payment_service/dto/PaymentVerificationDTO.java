package com.self.payment_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentVerificationDTO {

    @NotBlank(message = "Payment identifier cannot be blank")
    private String paymentIdentifier;
}
