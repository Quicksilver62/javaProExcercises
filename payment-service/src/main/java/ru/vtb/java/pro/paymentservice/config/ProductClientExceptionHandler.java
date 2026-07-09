package ru.vtb.java.pro.paymentservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;
import ru.vtb.java.pro.paymentservice.dto.ErrorResponseDto;
import ru.vtb.java.pro.paymentservice.exceptions.ProductServiceUnavailableException;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;

@Component
@RequiredArgsConstructor
public class ProductClientExceptionHandler implements ResponseErrorHandler {

    private final ObjectMapper objectMapper;

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().isError();
    }

    @Override
    public void handleError(URI url, HttpMethod method, ClientHttpResponse response) throws IOException {
        if (response.getStatusCode().is5xxServerError()) {
            ErrorResponseDto errorResponseDto = objectMapper.readValue(response.getBody(), ErrorResponseDto.class);
            throw new ProductServiceUnavailableException(errorResponseDto.error());
        }
    }
}
