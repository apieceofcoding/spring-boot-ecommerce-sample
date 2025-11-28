package com.sample.ecommerce.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    @DisplayName("주문을 생성하면 CREATED 상태로 생성된다")
    void createOrder() {
        // when
        Order order = Order.create();

        // then
        assertNotNull(order);
        assertNotNull(order.getOrderId());
        assertEquals(OrderStatus.CREATED, order.getStatus());
        assertNull(order.getPaidAt());
        assertNull(order.getCompletedAt());
    }

    @Test
    @DisplayName("orderId는 날짜 8자리 + 8자리 숫자 형식으로 생성된다")
    void orderIdFormat() {
        // when
        Order order = Order.create();

        // then
        assertNotNull(order.getOrderId());
        assertEquals(16, order.getOrderId().length());
        assertTrue(order.getOrderId().matches("\\d{16}"));
    }

    @Test
    @DisplayName("CREATED 상태의 주문을 PAID로 변경할 수 있다")
    void pay() {
        // given
        Order order = Order.create();

        // when
        order.pay();

        // then
        assertEquals(OrderStatus.PAID, order.getStatus());
        assertNotNull(order.getPaidAt());
    }

    @Test
    @DisplayName("CREATED 상태가 아닌 주문은 PAID로 변경할 수 없다")
    void payFromNonCreatedStatus() {
        // given
        Order order = Order.create();
        order.pay();

        // when & then
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                order::pay);
        assertEquals("Can only mark CREATED orders as paid", exception.getMessage());
    }

    @Test
    @DisplayName("PAID 상태의 주문을 COMPLETE로 변경할 수 있다")
    void complete() {
        // given
        Order order = Order.create();
        order.pay();

        // when
        order.complete();

        // then
        assertEquals(OrderStatus.COMPLETE, order.getStatus());
        assertNotNull(order.getCompletedAt());
    }

    @Test
    @DisplayName("PAID 상태가 아닌 주문은 COMPLETE로 변경할 수 없다")
    void completeFromNonPaidStatus() {
        // given
        Order order = Order.create();

        // when & then
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                order::complete);
        assertEquals("Can only mark PAID orders as complete", exception.getMessage());
    }

    @Test
    @DisplayName("주문 상태를 직접 변경할 수 있다")
    void updateStatus() {
        // given
        Order order = Order.create();

        // when
        order.updateStatus(OrderStatus.PAID);

        // then
        assertEquals(OrderStatus.PAID, order.getStatus());
    }

    @Test
    @DisplayName("주문 상태를 null로 변경할 수 없다")
    void updateStatusWithNull() {
        // given
        Order order = Order.create();

        // when & then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> order.updateStatus(null));
        assertEquals("Status cannot be null", exception.getMessage());
    }
}
