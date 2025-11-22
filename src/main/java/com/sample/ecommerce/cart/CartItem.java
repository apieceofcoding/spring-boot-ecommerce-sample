package com.sample.ecommerce.cart;

import com.sample.ecommerce.product.Product;
import com.sample.ecommerce.product.ProductOption;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Table(name = "cart_item")
@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_option_id")
    private ProductOption productOption;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private boolean deleted = false;

    public void update(Integer quantity) {
        if (quantity != null) {
            checkQuantityPositive(quantity);
            this.quantity = quantity;
        }
    }

    public void updateProductOption(ProductOption productOption) {
        this.productOption = productOption;
    }

    private static void checkQuantityPositive(Integer quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
    }

    public void delete() {
        if (deleted) {
            throw new IllegalStateException("CartItem is already deleted");
        }
        deleted = true;
    }

    public static CartItem create(Product product, ProductOption productOption, Integer quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (quantity == null) {
            throw new IllegalArgumentException("Quantity cannot be null");
        }
        checkQuantityPositive(quantity);

        CartItem cartItem = new CartItem();
        cartItem.product = product;
        cartItem.productOption = productOption;
        cartItem.quantity = quantity;
        return cartItem;
    }
}
