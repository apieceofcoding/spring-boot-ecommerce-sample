package com.sample.ecommerce.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "minio")
public record MinioProperty(
        String endpoint,
        String accessKey,
        String secretKey,
        String bucket
) {
}
