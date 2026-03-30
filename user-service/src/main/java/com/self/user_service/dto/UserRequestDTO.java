package com.self.user_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDTO {

    @NotBlank(message = "Name cannot be empty")
    private String fullName;

    @Email(message = "Invalid email format")
    private String emailAddress;

    @NotBlank(message = "Password cannot be empty")
    private String rawPassword;

    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    private String mobileNumber;

    @NotBlank(message = "Address cannot be empty")
    private String userAddress;

    private String role;
}
