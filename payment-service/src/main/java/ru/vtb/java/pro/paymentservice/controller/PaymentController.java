package ru.vtb.java.pro.paymentservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.vtb.java.pro.paymentservice.dto.PaymentResponseDto;
import ru.vtb.java.pro.paymentservice.dto.PaymentRequestDto;
import ru.vtb.java.pro.paymentservice.dto.ProductDto;
import ru.vtb.java.pro.paymentservice.services.PaymentService;

@RestController
@RequestMapping("api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/user/{id}/products")
    public Page<ProductDto> getProducts(@PathVariable Long id, Pageable pageable) {
        return paymentService.getProducts(id, pageable);
    }

    @PostMapping("/execute")
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentResponseDto executePayment(@RequestBody PaymentRequestDto request) throws InstantiationException {
        return paymentService.executePayment(request);
    }
}
