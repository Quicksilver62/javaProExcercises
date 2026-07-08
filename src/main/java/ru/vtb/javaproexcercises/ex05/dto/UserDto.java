package ru.vtb.javaproexcercises.ex05.dto;

import java.util.List;

public record UserDto(Long id, String username, List<ProductDto> products) {
}
