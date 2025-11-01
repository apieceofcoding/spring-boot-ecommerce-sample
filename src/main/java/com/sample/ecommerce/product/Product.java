package com.sample.ecommerce.product;

import jakarta.persistence.*;
import lombok.Getter;

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

    public static Product create(
            String title,
            BigDecimal listPrice,
            BigDecimal discountPrice,
            String thumbnailUrl
    ) {
        Product product = new Product();
        product.title = title;
        product.listPrice = listPrice;
        product.discountPrice = discountPrice;
        product.thumbnailUrl = thumbnailUrl;
        return product;
    }
}
