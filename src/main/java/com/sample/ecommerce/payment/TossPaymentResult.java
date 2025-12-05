package com.sample.ecommerce.payment;

public sealed interface TossPaymentResult permits TossPaymentResult.Success, TossPaymentResult.Failure {

    record Success(TossPaymentResponse response) implements TossPaymentResult {
    }

    record Failure(String code, String message) implements TossPaymentResult {
        public String getFormattedError() {
            return String.format("[%s] %s", code, message);
        }
    }
}
