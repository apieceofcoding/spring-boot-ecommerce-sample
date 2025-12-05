package com.sample.ecommerce.payment;

public record TossPaymentErrorResponse(
        String code,
        String message
) {
}
