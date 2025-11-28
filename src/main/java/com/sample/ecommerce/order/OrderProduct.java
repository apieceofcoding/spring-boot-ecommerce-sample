package com.sample.ecommerce.order;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Table(name = "order_products")
@Entity
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Order order;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private String productTitle;

    private Long productOptionId;

    @Column(nullable = false)
    private BigDecimal productPrice;

    private String productOptionName;

    @Column(nullable = false)
    private Integer quantity;

    public static OrderProduct create(
            Order order,
            Long productId,
            String productTitle,
            Long productOptionId,
            BigDecimal productPrice,
            String productOptionName,
            Integer quantity
    ) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
        if (productTitle == null || productTitle.isBlank()) {
            throw new IllegalArgumentException("Product title cannot be null or blank");
        }
        if (productPrice == null) {
            throw new IllegalArgumentException("Product price cannot be null");
        }
        if (productPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Product price cannot be negative");
        }
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        OrderProduct orderProduct = new OrderProduct();
        orderProduct.order = order;
        orderProduct.productId = productId;
        orderProduct.productTitle = productTitle;
        orderProduct.productOptionId = productOptionId;
        orderProduct.productPrice = productPrice;
        orderProduct.productOptionName = productOptionName;
        orderProduct.quantity = quantity;
        return orderProduct;
    }
}
