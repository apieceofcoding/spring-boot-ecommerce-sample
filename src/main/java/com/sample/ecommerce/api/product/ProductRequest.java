package com.sample.ecommerce.api.product;

import java.math.BigDecimal;

public record ProductRequest(
        String title,
        BigDecimal listPrice,
        BigDecimal discountPrice
) {
}
