package com.self.user_service.service;

import com.self.user_service.dto.LoginRequestDTO;
import com.self.user_service.dto.UserRequestDTO;
import com.self.user_service.dto.UserResponseDTO;
import com.self.user_service.entity.User;
import com.self.user_service.exception.EmailAlreadyExistsException;
import com.self.user_service.exception.InvalidCredentialsException;
import com.self.user_service.exception.UserNotFoundException;
import com.self.user_service.mapper.UserMapper;
import com.self.user_service.repository.UserRepository;
import com.self.user_service.auth.JwtUtil;
import com.self.user_service.util.ResponseStructure;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public ResponseStructure<UserResponseDTO> registerUser(UserRequestDTO requestDTO) {

        Optional<User> existingUser = userRepository.findByEmail(requestDTO.getEmailAddress());

        if (existingUser.isPresent()) {
            throw new EmailAlreadyExistsException("User with this email already exists");
        }

        User user = userMapper.toEntity(requestDTO);

        // Encrypt password
        user.setPassword(passwordEncoder.encode(requestDTO.getRawPassword()));

        // Assign default role if not present
        if (user.getRole() == null) {
            user.setRole("USER");
        }

        User savedUser = userRepository.save(user);

        UserResponseDTO responseDTO = userMapper.toResponseDTO(savedUser);

        return ResponseStructure.build(
                HttpStatus.CREATED,
                "User registered successfully",
                responseDTO
        );
    }

    @Override
    public ResponseStructure<String> loginUser(LoginRequestDTO loginDTO) {

        User user = userRepository.findByEmail(loginDTO.getEmailAddress())
                .orElseThrow(() -> new UserNotFoundException("User not found with this email"));

        boolean passwordMatch = passwordEncoder.matches(
                loginDTO.getPassword(),
                user.getPassword()
        );

        if (!passwordMatch) {
            throw new InvalidCredentialsException("Invalid password");
        }

        // Generate JWT token with email and role
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

        return ResponseStructure.build(
                HttpStatus.OK,
                "Login successful",
                token
        );
    }

    @Override
    public ResponseStructure<UserResponseDTO> getUserById(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + userId));

        UserResponseDTO responseDTO = userMapper.toResponseDTO(user);

        return ResponseStructure.build(
                HttpStatus.OK,
                "User fetched successfully",
                responseDTO
        );
    }

    @Override
    public ResponseStructure<UserResponseDTO> updateUser(Long userId, UserRequestDTO requestDTO) {

        // Get logged-in user email
        String loggedInEmail = (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        // Get logged-in user from DB
        User loggedInUser = userRepository.findByEmail(loggedInEmail)
                .orElseThrow(() -> new UserNotFoundException("Logged in user not found"));

        // Check if user is updating himself
        if (!loggedInUser.getUserId().equals(userId)) {
            throw new RuntimeException("You can only update your own profile");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + userId));

        user.setName(requestDTO.getFullName());
        user.setEmail(requestDTO.getEmailAddress());
        user.setPhoneNumber(requestDTO.getMobileNumber());
        user.setAddress(requestDTO.getUserAddress());

        if (requestDTO.getRawPassword() != null && !requestDTO.getRawPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(requestDTO.getRawPassword()));
        }

        User updatedUser = userRepository.save(user);

        return ResponseStructure.build(
                HttpStatus.OK,
                "User updated successfully",
                userMapper.toResponseDTO(updatedUser)
        );
    }

    @Override
    public ResponseStructure<String> deleteUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + userId));

        userRepository.delete(user);

        return ResponseStructure.build(
                HttpStatus.OK,
                "User deleted successfully",
                "User removed from system"
        );
    }

    @Override
    public ResponseStructure<UserResponseDTO> registerAdmin(UserRequestDTO requestDTO) {
        Optional<User> existingUser = userRepository.findByEmail(requestDTO.getEmailAddress());

        if (existingUser.isPresent()) {
            throw new EmailAlreadyExistsException("User with this email already exists");
        }

        User user = userMapper.toEntity(requestDTO);

        user.setPassword(passwordEncoder.encode(requestDTO.getRawPassword()));

        user.setRole("ADMIN");   // force admin role

        User savedUser = userRepository.save(user);

        UserResponseDTO responseDTO = userMapper.toResponseDTO(savedUser);

        return ResponseStructure.build(
                HttpStatus.CREATED,
                "Admin registered successfully",
                responseDTO
        );
    }

    public Long getUserIdByEmail(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found with email: " + email));

        return user.getUserId();
    }
}
