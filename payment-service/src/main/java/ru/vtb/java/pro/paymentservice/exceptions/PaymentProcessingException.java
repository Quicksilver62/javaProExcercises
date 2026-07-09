package ru.vtb.java.pro.paymentservice.exceptions;

import lombok.Getter;

@Getter
public class PaymentProcessingException extends RuntimeException {

    private final int statusCode;

    public PaymentProcessingException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
