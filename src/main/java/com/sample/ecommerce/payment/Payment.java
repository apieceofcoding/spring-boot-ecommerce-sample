package com.sample.ecommerce.payment;

import com.sample.ecommerce.order.Order;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Table(name = "payments")
@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String paymentKey;

    @Column(nullable = false)
    private String orderId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    private String method;

    private LocalDateTime approvedAt;

    @Column(length = 1000)
    private String failReason;

    private LocalDateTime createdAt;

    public static Payment create(String paymentKey, String orderId, BigDecimal amount,
                                  String method, LocalDateTime approvedAt) {
        Payment payment = new Payment();
        payment.paymentKey = paymentKey;
        payment.orderId = orderId;
        payment.amount = amount;
        payment.status = PaymentStatus.DONE;
        payment.method = method;
        payment.approvedAt = approvedAt;
        payment.createdAt = LocalDateTime.now();
        return payment;
    }

    public static Payment createFailed(String paymentKey, String orderId, BigDecimal amount, String failReason) {
        Payment payment = new Payment();
        payment.paymentKey = paymentKey;
        payment.orderId = orderId;
        payment.amount = amount;
        payment.status = PaymentStatus.FAILED;
        payment.failReason = failReason;
        payment.createdAt = LocalDateTime.now();
        return payment;
    }
}
