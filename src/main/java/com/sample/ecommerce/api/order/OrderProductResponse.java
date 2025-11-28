package com.sample.ecommerce.api.order;

import com.sample.ecommerce.order.OrderProduct;

import java.math.BigDecimal;

public record OrderProductResponse(
        Long id,
        Long productId,
        String productTitle,
        Long productOptionId,
        BigDecimal productPrice,
        String productOptionName,
        Integer quantity
) {
    public static OrderProductResponse from(OrderProduct orderProduct) {
        return new OrderProductResponse(
                orderProduct.getId(),
                orderProduct.getProductId(),
                orderProduct.getProductTitle(),
                orderProduct.getProductOptionId(),
                orderProduct.getProductPrice(),
                orderProduct.getProductOptionName(),
                orderProduct.getQuantity()
        );
    }
}
