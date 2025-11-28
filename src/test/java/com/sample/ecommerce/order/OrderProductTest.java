package com.sample.ecommerce.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class OrderProductTest {

    @Test
    @DisplayName("OrderProduct를 생성할 수 있다")
    void createOrderProduct() {
        // given
        Order order = Order.create();
        Long productId = 1L;
        String productTitle = "MacBook Air M3";
        Long productOptionId = 1L;
        BigDecimal productPrice = new BigDecimal("1690000");
        String productOptionName = "16GB RAM";
        Integer quantity = 2;

        // when
        OrderProduct orderProduct = OrderProduct.create(
                order, productId, productTitle, productOptionId, productPrice, productOptionName, quantity
        );

        // then
        assertNotNull(orderProduct);
        assertEquals(order, orderProduct.getOrder());
        assertEquals(productId, orderProduct.getProductId());
        assertEquals(productTitle, orderProduct.getProductTitle());
        assertEquals(productOptionId, orderProduct.getProductOptionId());
        assertEquals(productPrice, orderProduct.getProductPrice());
        assertEquals(productOptionName, orderProduct.getProductOptionName());
        assertEquals(quantity, orderProduct.getQuantity());
    }

    @Test
    @DisplayName("productOptionId 없이 OrderProduct를 생성할 수 있다")
    void createOrderProductWithoutOption() {
        // given
        Order order = Order.create();
        Long productId = 1L;
        String productTitle = "MacBook Air M3";
        BigDecimal productPrice = new BigDecimal("1690000");
        Integer quantity = 1;

        // when
        OrderProduct orderProduct = OrderProduct.create(
                order, productId, productTitle, null, productPrice, null, quantity
        );

        // then
        assertNotNull(orderProduct);
        assertNull(orderProduct.getProductOptionId());
        assertNull(orderProduct.getProductOptionName());
    }

    @Test
    @DisplayName("Order가 null이면 OrderProduct를 생성할 수 없다")
    void createOrderProductWithNullOrder() {
        // given
        Long productId = 1L;
        String productTitle = "MacBook Air M3";
        BigDecimal productPrice = new BigDecimal("1690000");
        Integer quantity = 1;

        // when & then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> OrderProduct.create(null, productId, productTitle, null, productPrice, null, quantity));
        assertEquals("Order cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("상품명이 null이면 OrderProduct를 생성할 수 없다")
    void createOrderProductWithNullProductTitle() {
        // given
        Order order = Order.create();
        Long productId = 1L;
        BigDecimal productPrice = new BigDecimal("1690000");
        Integer quantity = 1;

        // when & then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> OrderProduct.create(order, productId, null, null, productPrice, null, quantity));
        assertEquals("Product title cannot be null or blank", exception.getMessage());
    }

    @Test
    @DisplayName("상품명이 빈 문자열이면 OrderProduct를 생성할 수 없다")
    void createOrderProductWithBlankProductTitle() {
        // given
        Order order = Order.create();
        Long productId = 1L;
        BigDecimal productPrice = new BigDecimal("1690000");
        Integer quantity = 1;

        // when & then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> OrderProduct.create(order, productId, "   ", null, productPrice, null, quantity));
        assertEquals("Product title cannot be null or blank", exception.getMessage());
    }

    @Test
    @DisplayName("상품 가격이 null이면 OrderProduct를 생성할 수 없다")
    void createOrderProductWithNullPrice() {
        // given
        Order order = Order.create();
        Long productId = 1L;
        String productTitle = "MacBook Air M3";
        Integer quantity = 1;

        // when & then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> OrderProduct.create(order, productId, productTitle, null, null, null, quantity));
        assertEquals("Product price cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("상품 가격이 음수면 OrderProduct를 생성할 수 없다")
    void createOrderProductWithNegativePrice() {
        // given
        Order order = Order.create();
        Long productId = 1L;
        String productTitle = "MacBook Air M3";
        BigDecimal productPrice = new BigDecimal("-1000");
        Integer quantity = 1;

        // when & then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> OrderProduct.create(order, productId, productTitle, null, productPrice, null, quantity));
        assertEquals("Product price cannot be negative", exception.getMessage());
    }

    @Test
    @DisplayName("상품 ID가 null이면 OrderProduct를 생성할 수 없다")
    void createOrderProductWithNullProductId() {
        // given
        Order order = Order.create();
        String productTitle = "MacBook Air M3";
        BigDecimal productPrice = new BigDecimal("1690000");
        Integer quantity = 1;

        // when & then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> OrderProduct.create(order, null, productTitle, null, productPrice, null, quantity));
        assertEquals("Product ID cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("수량이 null이면 OrderProduct를 생성할 수 없다")
    void createOrderProductWithNullQuantity() {
        // given
        Order order = Order.create();
        Long productId = 1L;
        String productTitle = "MacBook Air M3";
        BigDecimal productPrice = new BigDecimal("1690000");

        // when & then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> OrderProduct.create(order, productId, productTitle, null, productPrice, null, null));
        assertEquals("Quantity must be positive", exception.getMessage());
    }

    @Test
    @DisplayName("수량이 0이면 OrderProduct를 생성할 수 없다")
    void createOrderProductWithZeroQuantity() {
        // given
        Order order = Order.create();
        Long productId = 1L;
        String productTitle = "MacBook Air M3";
        BigDecimal productPrice = new BigDecimal("1690000");

        // when & then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> OrderProduct.create(order, productId, productTitle, null, productPrice, null, 0));
        assertEquals("Quantity must be positive", exception.getMessage());
    }

    @Test
    @DisplayName("수량이 음수면 OrderProduct를 생성할 수 없다")
    void createOrderProductWithNegativeQuantity() {
        // given
        Order order = Order.create();
        Long productId = 1L;
        String productTitle = "MacBook Air M3";
        BigDecimal productPrice = new BigDecimal("1690000");

        // when & then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> OrderProduct.create(order, productId, productTitle, null, productPrice, null, -1));
        assertEquals("Quantity must be positive", exception.getMessage());
    }
}
