package com.sample.ecommerce.api.productoption;

import java.math.BigDecimal;

public record ProductOptionRequest(
        String name,
        BigDecimal priceDiff,
        Integer stock
) {
}
