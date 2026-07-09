package ru.vtb.java.pro.paymentservice.dto;

import ru.vtb.java.pro.paymentservice.enums.PaymentStatus;

public record PaymentResponseDto(Long userId, Long productId, Double amount, PaymentStatus status, Double userLimit) {
}
