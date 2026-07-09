package ru.vtb.javaproexcercises.ex05.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.vtb.javaproexcercises.ex05.dto.ProductDto;
import ru.vtb.javaproexcercises.ex05.exceptions.CustomException;
import ru.vtb.javaproexcercises.ex05.mappers.ProductMapper;
import ru.vtb.javaproexcercises.ex05.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    public ProductDto findProductById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toDto)
                .orElseThrow(() -> new CustomException("Product not found with id: " + id, 404));
    }

    public Page<ProductDto> findProductsByUserId(Long userId, Pageable pageable) {
        return productRepository.findAllByUserId(userId, pageable)
                .map(productMapper::toDto);
    }
}
