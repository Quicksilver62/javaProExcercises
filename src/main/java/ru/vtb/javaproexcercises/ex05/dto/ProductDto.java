package ru.vtb.javaproexcercises.ex05.dto;

import ru.vtb.javaproexcercises.ex05.enums.ProductType;

public record ProductDto(Long id, String account, Double amount, ProductType productType) {
}
