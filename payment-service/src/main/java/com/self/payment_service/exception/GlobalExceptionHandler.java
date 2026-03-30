package com.self.payment_service.exception;

import com.self.payment_service.util.ResponseStructure;
import com.self.payment_service.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PaymentNotFoundException.class)
    public ResponseEntity<ResponseStructure<String>> handlePaymentNotFoundException(
            PaymentNotFoundException ex) {

        ResponseStructure<String> response =
                ResponseUtil.buildResponse(
                        HttpStatus.NOT_FOUND.value(),
                        ex.getMessage(),
                        null
                );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PaymentAlreadyProcessedException.class)
    public ResponseEntity<ResponseStructure<String>> handlePaymentAlreadyProcessedException(
            PaymentAlreadyProcessedException ex) {

        ResponseStructure<String> response =
                ResponseUtil.buildResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        ex.getMessage(),
                        null
                );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidPaymentRequestException.class)
    public ResponseEntity<ResponseStructure<String>> handleInvalidPaymentRequestException(
            InvalidPaymentRequestException ex) {

        ResponseStructure<String> response =
                ResponseUtil.buildResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        ex.getMessage(),
                        null
                );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderServiceUnavailableException.class)
    public ResponseEntity<ResponseStructure<String>> handleOrderServiceUnavailableException(
            OrderServiceUnavailableException ex) {

        ResponseStructure<String> response =
                ResponseUtil.buildResponse(
                        HttpStatus.SERVICE_UNAVAILABLE.value(),
                        ex.getMessage(),
                        null
                );

        return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseStructure<String>> handleGenericException(Exception ex) {

        ResponseStructure<String> response =
                ResponseUtil.buildResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Something went wrong",
                        null
                );

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
