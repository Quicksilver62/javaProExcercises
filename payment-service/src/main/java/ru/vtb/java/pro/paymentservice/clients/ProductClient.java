package ru.vtb.java.pro.paymentservice.clients;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.vtb.java.pro.paymentservice.dto.ProductDto;
import ru.vtb.java.pro.paymentservice.pojo.PageResponse;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductClient {

    private final RestTemplate productClientTemplate;

    public Page<ProductDto> getProductsByUserId(Long userId, Pageable pageable) {
        ParameterizedTypeReference<PageResponse<ProductDto>> responseType = new ParameterizedTypeReference<>() {};

        String url = String.format("/api/v1/products/by-user/%d?page=%d&size=%d",
                userId,
                pageable.getPageNumber(),
                pageable.getPageSize());

        log.info("Fetching products for user: {} from {}", userId, url);

        ResponseEntity<PageResponse<ProductDto>> response = productClientTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                responseType
        );

        PageResponse<ProductDto> pageResponse = response.getBody();

        if (pageResponse == null) {
            return new PageImpl<>(List.of());
        }

        return pageResponse.toPage(pageable);
    }
}
