package ru.vtb.java.pro.paymentservice.dto;

import ru.vtb.java.pro.paymentservice.enums.ProductType;

public record ProductDto(Long id, String account, Double amount, ProductType productType) {
}
