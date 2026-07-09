package ru.vtb.java.pro.paymentservice.controller.exceptionHandlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.vtb.java.pro.paymentservice.dto.ErrorResponseDto;
import ru.vtb.java.pro.paymentservice.exceptions.InsufficientFundsException;
import ru.vtb.java.pro.paymentservice.exceptions.PaymentProcessingException;
import ru.vtb.java.pro.paymentservice.exceptions.ProductNotFoundException;
import ru.vtb.java.pro.paymentservice.exceptions.ProductServiceUnavailableException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PaymentProcessingException.class)
    public ResponseEntity<ErrorResponseDto> handle(PaymentProcessingException exception) {
        return ResponseEntity
                .status(exception.getStatusCode())
                .body(new ErrorResponseDto(exception.getMessage()));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleProductNotFound(ProductNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDto(exception.getMessage()));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleInsufficientFunds(InsufficientFundsException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDto(exception.getMessage()));
    }

    @ExceptionHandler(ProductServiceUnavailableException.class)
    public ResponseEntity<ErrorResponseDto> handleProductServiceUnavailable(ProductServiceUnavailableException exception) {
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(new ErrorResponseDto(exception.getMessage()));
    }
}
