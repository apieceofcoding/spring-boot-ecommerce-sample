package com.sample.ecommerce.payment;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "tosspayments")
public record TossPaymentsProperty(
        String baseUrl,
        String secretKey
) {
}
