package com.sample.ecommerce.api.productoption;

import com.sample.ecommerce.product.ProductOption;

import java.math.BigDecimal;

public record ProductOptionResponse(
        Long id,
        Long productId,
        String name,
        BigDecimal priceDiff,
        Integer stock
) {
    public static ProductOptionResponse from(ProductOption option) {
        return new ProductOptionResponse(
                option.getId(),
                option.getProduct().getId(),
                option.getName(),
                option.getPriceDiff(),
                option.getStock()
        );
    }
}
