package com.self.user_service.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {

    private Long id;

    private String fullName;

    private String emailAddress;

    private String mobileNumber;

    private String userAddress;
}
