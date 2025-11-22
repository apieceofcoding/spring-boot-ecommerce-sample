package com.sample.ecommerce.category;

import com.sample.ecommerce.product.Product;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Table(name = "category_product")
@Entity
public class CategoryProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public static CategoryProduct create(Category category, Product product) {
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }

        CategoryProduct categoryProduct = new CategoryProduct();
        categoryProduct.category = category;
        categoryProduct.product = product;
        return categoryProduct;
    }
}
