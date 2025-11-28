package com.sample.ecommerce.product;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Table(name = "product_options")
@Entity
public class ProductOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Product product;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal priceDiff;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    private boolean deleted = false;

    public void update(String name, BigDecimal priceDiff, Integer stock) {
        if (name != null && !name.isBlank()) {
            this.name = name;
        }
        if (priceDiff != null) {
            checkPriceDiffNotNegative(priceDiff);
            this.priceDiff = priceDiff;
        }
        if (stock != null) {
            checkStockNotNegative(stock);
            this.stock = stock;
        }
    }

    private static void checkPriceDiffNotNegative(BigDecimal priceDiff) {
        if (priceDiff.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("PriceDiff cannot be negative");
        }
    }

    private static void checkStockNotNegative(Integer stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }
    }

    public void delete() {
        if (deleted) {
            throw new IllegalStateException("ProductOption is already deleted");
        }
        deleted = true;
    }

    public static ProductOption create(Product product, String name, BigDecimal priceDiff, Integer stock) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }
        if (priceDiff == null) {
            throw new IllegalArgumentException("PriceDiff cannot be null");
        }
        checkPriceDiffNotNegative(priceDiff);
        if (stock == null) {
            throw new IllegalArgumentException("Stock cannot be null");
        }
        checkStockNotNegative(stock);

        ProductOption option = new ProductOption();
        option.product = product;
        option.name = name;
        option.priceDiff = priceDiff;
        option.stock = stock;
        return option;
    }
}