package com.sample.ecommerce.payment;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TossPaymentResponse(
        String paymentKey,
        String orderId,
        String orderName,
        String status,
        BigDecimal totalAmount,
        String method,
        LocalDateTime approvedAt,
        CardInfo card
) {

    @Getter
    @Setter
    public static class CardInfo {
        private String company;
        private String number;
        private String installmentPlanMonths;
        private String approveNo;
        private String cardType;
        private String ownerType;
        private String acquireStatus;
    }
}
