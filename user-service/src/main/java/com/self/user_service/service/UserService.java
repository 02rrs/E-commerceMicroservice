package com.self.user_service.service;

import com.self.user_service.dto.LoginRequestDTO;
import com.self.user_service.dto.UserRequestDTO;
import com.self.user_service.dto.UserResponseDTO;
import com.self.user_service.util.ResponseStructure;

public interface UserService {

    ResponseStructure<UserResponseDTO> registerUser(UserRequestDTO requestDTO);

    ResponseStructure<String> loginUser(LoginRequestDTO loginDTO);

    ResponseStructure<UserResponseDTO> getUserById(Long userId);

    ResponseStructure<UserResponseDTO> updateUser(Long userId, UserRequestDTO requestDTO);

    ResponseStructure<String> deleteUser(Long userId);

    ResponseStructure<UserResponseDTO> registerAdmin(UserRequestDTO requestDTO);

    Long getUserIdByEmail(String email);
}
