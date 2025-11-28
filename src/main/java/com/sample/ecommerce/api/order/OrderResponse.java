package com.sample.ecommerce.api.order;

import com.sample.ecommerce.order.Order;
import com.sample.ecommerce.order.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        Long id,
        String orderId,
        OrderStatus status,
        LocalDateTime paidAt,
        LocalDateTime completedAt,
        List<OrderProductResponse> orderProducts
) {
    public static OrderResponse from(Order order, List<OrderProductResponse> orderProducts) {
        return new OrderResponse(
                order.getId(),
                order.getOrderId(),
                order.getStatus(),
                order.getPaidAt(),
                order.getCompletedAt(),
                orderProducts
        );
    }
}
