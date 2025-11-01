package com.sample.ecommerce.product;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;

@Getter
@Table(name = "products")
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private BigDecimal listPrice;

    private BigDecimal discountPrice;

    private String thumbnailUrl;

    @Column(nullable = false)
    private boolean deleted = false;

    public void update(
            String title,
            BigDecimal listPrice,
            BigDecimal discountPrice,
            String thumbnailUrl
    ) {
        if (title != null && !title.isBlank()) {
            this.title = title;
        }
        if (listPrice != null) {
            checkPriceNotNegative(listPrice, "List price");
            this.listPrice = listPrice;
        }
        if (discountPrice != null) {
            checkPriceNotNegative(discountPrice, "Discount price");
            checkDiscountPrice(listPrice != null ? listPrice : this.listPrice, discountPrice);
            this.discountPrice = discountPrice;
        }
        if (thumbnailUrl != null && !thumbnailUrl.isBlank()) {
            this.thumbnailUrl = thumbnailUrl;
        }
    }

    public void delete() {
        if (deleted) {
            throw new IllegalStateException("Product is already deleted");
        }
        deleted = true;
    }

    private static void checkPriceNotNegative(BigDecimal price, String fieldName) {
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(fieldName + " cannot be negative");
        }
    }

    private static void checkDiscountPrice(BigDecimal listPrice, BigDecimal discountPrice) {
        if (listPrice != null && discountPrice.compareTo(listPrice) > 0) {
            throw new IllegalArgumentException("Discount price cannot be greater than list price");
        }
    }

    public static Product create(
            String title,
            BigDecimal listPrice,
            BigDecimal discountPrice,
            String thumbnailUrl
    ) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be null or blank");
        }
        if (listPrice == null) {
            throw new IllegalArgumentException("List price cannot be null");
        }
        checkPriceNotNegative(listPrice, "List price");

        if (discountPrice != null) {
            checkPriceNotNegative(discountPrice, "Discount price");
            checkDiscountPrice(listPrice, discountPrice);
        }

        Product product = new Product();
        product.title = title;
        product.listPrice = listPrice;
        product.discountPrice = discountPrice;
        product.thumbnailUrl = thumbnailUrl;
        return product;
    }
}
