package ru.vtb.java.pro.productservice.controller.exceptionHandlers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.vtb.java.pro.productservice.dto.ErrorResponseDto;
import ru.vtb.java.pro.productservice.exceptions.CustomException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponseDto> handle(CustomException exception) {
        return ResponseEntity
                .status(exception.getStatusCode())
                .body(new ErrorResponseDto(exception.getMessage()));
    }
}
