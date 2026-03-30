package com.self.product_service.exception;

import com.self.product_service.util.ResponseStructure;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ResponseStructure<String>> handleProductNotFound(ProductNotFoundException ex) {

        ResponseStructure<String> response = new ResponseStructure<>();

        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setMessage("Product not found");
        response.setData(ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ResponseStructure<String>> handleProductNotFound(InsufficientStockException ex) {

        ResponseStructure<String> response = new ResponseStructure<>();

        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setMessage("Insufficient Stock");
        response.setData(ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
