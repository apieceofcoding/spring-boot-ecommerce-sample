package com.sample.ecommerce.api.cart;

public record CartItemRequest(
        Long productId,
        Long productOptionId,
        Integer quantity
) {
}
