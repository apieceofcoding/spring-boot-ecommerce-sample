package com.sample.ecommerce.api.product;

import com.sample.ecommerce.product.Product;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String title,
        BigDecimal listPrice,
        BigDecimal discountPrice,
        String thumbnailUrl
) {
    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getTitle(),
                product.getListPrice(),
                product.getDiscountPrice(),
                product.getThumbnailUrl()
        );
    }
}
