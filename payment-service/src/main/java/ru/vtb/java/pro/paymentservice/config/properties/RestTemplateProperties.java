package ru.vtb.java.pro.paymentservice.config.properties;

import java.time.Duration;

public record RestTemplateProperties(String url, Duration connectTimeout, Duration readTimeout) {
}
