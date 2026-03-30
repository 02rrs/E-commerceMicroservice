package com.self.order_service.exception;

import com.self.order_service.util.ResponseStructure;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ResponseStructure<String>> handleOrderNotFound(OrderNotFoundException ex) {

        ResponseStructure<String> structure = new ResponseStructure<>();
        structure.setStatusCode(HttpStatus.NOT_FOUND.value());
        structure.setMessage("Order not found");
        structure.setData(ex.getMessage());

        return new ResponseEntity<>(structure, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ResponseStructure<String>> handleProductNotFound(ProductNotFoundException ex) {

        ResponseStructure<String> structure = new ResponseStructure<>();
        structure.setStatusCode(HttpStatus.NOT_FOUND.value());
        structure.setMessage("Product not found");
        structure.setData(ex.getMessage());

        return new ResponseEntity<>(structure, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ResponseStructure<String>> handleUserNotFound(UserNotFoundException ex) {

        ResponseStructure<String> structure = new ResponseStructure<>();
        structure.setStatusCode(HttpStatus.NOT_FOUND.value());
        structure.setMessage("User not found");
        structure.setData(ex.getMessage());

        return new ResponseEntity<>(structure, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ResponseStructure<String>> handleStockException(InsufficientStockException ex) {

        ResponseStructure<String> structure = new ResponseStructure<>();
        structure.setStatusCode(HttpStatus.BAD_REQUEST.value());
        structure.setMessage("Insufficient stock");
        structure.setData(ex.getMessage());

        return new ResponseEntity<>(structure, HttpStatus.BAD_REQUEST);
    }
}
