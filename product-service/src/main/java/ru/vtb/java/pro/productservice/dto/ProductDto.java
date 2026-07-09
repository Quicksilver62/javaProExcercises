package ru.vtb.java.pro.productservice.dto;

import ru.vtb.java.pro.productservice.enums.ProductType;

public record ProductDto(Long id, String account, Double amount, ProductType productType) {
}
