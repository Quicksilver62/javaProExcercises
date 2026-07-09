package ru.vtb.javaproexcercises.ex05.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.vtb.javaproexcercises.ex05.dto.ErrorResponseDto;
import ru.vtb.javaproexcercises.ex05.exceptions.CustomException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponseDto> handle(CustomException exception) {
        return ResponseEntity
                .status(exception.getStatusCode())
                .body(new ErrorResponseDto(exception.getMessage()));
    }
}
