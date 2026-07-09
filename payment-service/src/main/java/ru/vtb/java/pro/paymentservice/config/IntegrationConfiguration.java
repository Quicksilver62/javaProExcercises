package ru.vtb.java.pro.paymentservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.restclient.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.vtb.java.pro.paymentservice.config.properties.ClientProperties;
import ru.vtb.java.pro.paymentservice.config.properties.RestTemplateProperties;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(ClientProperties.class)
public class IntegrationConfiguration {

    private final ClientProperties clientProperties;

    @Bean
    public RestTemplate productClientTemplate() {
        RestTemplateProperties restTemplateProperties = clientProperties.getProductClient();
        return new RestTemplateBuilder()
                .baseUri(restTemplateProperties.url())
                .connectTimeout(restTemplateProperties.connectTimeout())
                .readTimeout(restTemplateProperties.readTimeout())
                .build();
    }

    @Bean
    public RestTemplate limitClientTemplate() {
        RestTemplateProperties restTemplateProperties = clientProperties.getLimitClient();
        return new RestTemplateBuilder()
                .baseUri(restTemplateProperties.url())
                .connectTimeout(restTemplateProperties.connectTimeout())
                .readTimeout(restTemplateProperties.readTimeout())
                .build();
    }
}
