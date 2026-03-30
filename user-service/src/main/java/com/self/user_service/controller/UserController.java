package com.self.user_service.controller;

import com.self.user_service.dto.LoginRequestDTO;
import com.self.user_service.dto.UserRequestDTO;
import com.self.user_service.dto.UserResponseDTO;
import com.self.user_service.service.UserService;
import com.self.user_service.util.ResponseStructure;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseStructure<UserResponseDTO>> registerUser(
            @Valid @RequestBody UserRequestDTO requestDTO) {

        return ResponseEntity.status(201)
                .body(userService.registerUser(requestDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseStructure<String>> loginUser(
            @Valid @RequestBody LoginRequestDTO loginDTO) {

        return ResponseEntity.ok(
                userService.loginUser(loginDTO)
        );
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ResponseStructure<UserResponseDTO>> getUserById(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                userService.getUserById(userId)
        );
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ResponseStructure<UserResponseDTO>> updateUser(
            @PathVariable Long userId,
            @Valid @RequestBody UserRequestDTO requestDTO) {

        return ResponseEntity.ok(
                userService.updateUser(userId, requestDTO)
        );
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ResponseStructure<String>> deleteUser(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                userService.deleteUser(userId)
        );
    }

    @PostMapping("/admin/register")
    public ResponseStructure<UserResponseDTO> registerAdmin(
            @RequestBody @Valid UserRequestDTO requestDTO){

        return userService.registerAdmin(requestDTO);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ResponseStructure<Long>> getUserIdByEmail(
            @PathVariable String email) {

        Long userId = userService.getUserIdByEmail(email);

        return ResponseEntity.ok(
                ResponseStructure.build(
                        HttpStatus.OK,
                        "User ID fetched successfully",
                        userId
                )
        );
    }
}
