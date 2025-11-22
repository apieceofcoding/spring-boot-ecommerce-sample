package com.sample.ecommerce.api.cart;

import com.sample.ecommerce.cart.CartItem;

public record CartItemResponse(
        Long id,
        Long productId,
        Long productOptionId,
        Integer quantity
) {
    public static CartItemResponse from(CartItem cartItem) {
        return new CartItemResponse(
                cartItem.getId(),
                cartItem.getProduct().getId(),
                cartItem.getProductOption() != null ? cartItem.getProductOption().getId() : null,
                cartItem.getQuantity()
        );
    }
}
