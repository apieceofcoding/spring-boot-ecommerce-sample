package com.sample.ecommerce.api.payment;

import com.sample.ecommerce.payment.Payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentResponse(
        Long id,
        String paymentKey,
        String orderId,
        BigDecimal amount,
        String status,
        String method,
        LocalDateTime approvedAt,
        LocalDateTime createdAt
) {
    public static PaymentResponse from(Payment payment) {
        return new PaymentResponse(
                payment.getId(),
                payment.getPaymentKey(),
                payment.getOrderId(),
                payment.getAmount(),
                payment.getStatus().name(),
                payment.getMethod(),
                payment.getApprovedAt(),
                payment.getCreatedAt()
        );
    }
}
