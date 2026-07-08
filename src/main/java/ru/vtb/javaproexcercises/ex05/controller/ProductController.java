package ru.vtb.javaproexcercises.ex05.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vtb.javaproexcercises.ex05.dto.ProductDto;
import ru.vtb.javaproexcercises.ex05.services.ProductService;

@RestController
@RequestMapping("api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public ProductDto findProductById(@PathVariable Long id) {
        return productService.findProductById(id);
    }

    @GetMapping("/by-user/{userId}")
    public Page<ProductDto> findProductsByUserId(@PathVariable Long userId, Pageable pageable) {
        return productService.findProductsByUserId(userId, pageable);
    }
}
