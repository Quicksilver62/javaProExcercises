package ru.vtb.java.pro.paymentservice.clients;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

//TODO реализовать интеграцию когда будет limit-service
@Slf4j
@Service
@RequiredArgsConstructor
public class LimitClient {

    private final RestTemplate limitClientTemplate;

    public Double getLimit() {
        return 100000.00;
    }

    public void upsertLimit(Double limit) {}
}
