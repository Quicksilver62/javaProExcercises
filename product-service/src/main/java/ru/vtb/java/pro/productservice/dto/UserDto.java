package ru.vtb.java.pro.productservice.dto;

import java.util.List;

public record UserDto(Long id, String username, List<ProductDto> products) {
}
