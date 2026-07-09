package ru.vtb.java.pro.paymentservice.config.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "clients")
@RequiredArgsConstructor
public class ClientProperties {

    private final RestTemplateProperties productClient;
}
