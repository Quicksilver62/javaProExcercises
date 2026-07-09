package ru.vtb.java.pro.paymentservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vtb.java.pro.paymentservice.clients.LimitClient;
import ru.vtb.java.pro.paymentservice.clients.ProductClient;
import ru.vtb.java.pro.paymentservice.dto.PaymentResponseDto;
import ru.vtb.java.pro.paymentservice.dto.PaymentRequestDto;
import ru.vtb.java.pro.paymentservice.dto.ProductDto;
import ru.vtb.java.pro.paymentservice.exceptions.PaymentProcessingException;
import ru.vtb.java.pro.paymentservice.exceptions.ProductNotFoundException;

import static ru.vtb.java.pro.paymentservice.enums.PaymentStatus.COMPLETED;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final ProductClient productClient;
    private final LimitClient limitClient;

    @Transactional
    public PaymentResponseDto executePayment(PaymentRequestDto request) throws InstantiationException {
        validateRequest(request);

        ProductDto product = productClient.getProductsByUserId(request.userId(), Pageable.unpaged())
                .getContent()
                .stream()
                .filter(it -> request.productId().equals(it.id()))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException(
                        String.format("Product with id %s not found", request.productId())));

        Double userLimit = limitClient.getLimit();

        if (userLimit < product.amount()) {
            throw new InstantiationException("Insufficient funds on product id: " + request.productId());
        }

        userLimit = userLimit - product.amount();

        limitClient.upsertLimit(userLimit);

        return new PaymentResponseDto(request.userId(), request.productId(), product.amount(), COMPLETED);
    }

    public Page<ProductDto> getProducts(Long id, Pageable pageable) {
        return productClient.getProductsByUserId(id, pageable);
    }

    private void validateRequest(PaymentRequestDto request) {
        if (request.userId() == null) {
            throw new PaymentProcessingException("User id is required", 400);
        }
        if (request.productId() == null) {
            throw new PaymentProcessingException("Product id is required", 400);
        }
    }
}
