package com.sample.ecommerce.api.payment;

import com.sample.ecommerce.payment.Payment;
import com.sample.ecommerce.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/api/v1/confirm")
    public PaymentResponse confirm(@RequestBody PaymentConfirmRequest request) {
        Payment payment = paymentService.confirm(
                request.paymentKey(),
                request.orderId(),
                request.amount()
        );
        return PaymentResponse.from(payment);
    }
}
