package com.self.user_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequestDTO {

    @Email(message = "Invalid email format")
    private String emailAddress;

    @NotBlank(message = "Password cannot be empty")
    private String password;
}
