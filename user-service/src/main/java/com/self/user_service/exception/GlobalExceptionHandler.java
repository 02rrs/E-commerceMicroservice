package com.self.user_service.exception;

import com.self.user_service.util.ResponseStructure;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ResponseStructure<String>> handleUserNotFound(UserNotFoundException ex){

        ResponseStructure<String> response = new ResponseStructure<>();

        response.setStatusCode(HttpStatus.NOT_FOUND.value());
        response.setMessage("User not found");
        response.setData(ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ResponseStructure<String>> handleEmailExists(EmailAlreadyExistsException ex){

        ResponseStructure<String> response = new ResponseStructure<>();

        response.setStatusCode(HttpStatus.CONFLICT.value());
        response.setMessage("Email already exists");
        response.setData(ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ResponseStructure<String>> handleInvalidCredentials(InvalidCredentialsException ex){

        ResponseStructure<String> response = new ResponseStructure<>();

        response.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        response.setMessage("Invalid credentials");
        response.setData(ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

}
