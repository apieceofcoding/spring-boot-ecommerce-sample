package com.sample.ecommerce.order;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Getter
@Table(name = "orders")
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String orderId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    private LocalDateTime paidAt;

    private LocalDateTime completedAt;

    public void updateStatus(OrderStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        this.status = status;
    }

    public void pay() {
        if (this.status != OrderStatus.CREATED) {
            throw new IllegalStateException("Can only mark CREATED orders as paid");
        }
        this.status = OrderStatus.PAID;
        this.paidAt = LocalDateTime.now();
    }

    public void complete() {
        if (this.status != OrderStatus.PAID) {
            throw new IllegalStateException("Can only mark PAID orders as complete");
        }
        this.status = OrderStatus.COMPLETE;
        this.completedAt = LocalDateTime.now();
    }

    public static Order create() {
        Order order = new Order();
        order.orderId = generateOrderId();
        order.status = OrderStatus.CREATED;
        return order;
    }

    private static String generateOrderId() {
        String datePart = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        long randomPart = Math.abs(new Random().nextLong() % 100000000L);
        return datePart + String.format("%08d", randomPart);
    }
}
