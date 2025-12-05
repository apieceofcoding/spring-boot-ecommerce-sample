package com.sample.ecommerce.api.payment;

public record PaymentConfirmRequest(
        String paymentKey,
        String orderId,
        Long amount
) {
}
