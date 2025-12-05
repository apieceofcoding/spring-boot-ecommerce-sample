package com.sample.ecommerce.payment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TossPaymentException extends RuntimeException {
    private final String code;
    private final String errorMessage;
}
