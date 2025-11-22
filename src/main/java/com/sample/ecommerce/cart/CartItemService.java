package com.sample.ecommerce.cart;

import com.sample.ecommerce.api.cart.CartItemRequest;
import com.sample.ecommerce.product.Product;
import com.sample.ecommerce.product.ProductOption;
import com.sample.ecommerce.product.ProductOptionRepository;
import com.sample.ecommerce.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;

    public CartItem create(CartItemRequest request) {
        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        ProductOption productOption = null;
        if (request.productOptionId() != null) {
            productOption = productOptionRepository.findById(request.productOptionId())
                    .orElseThrow(() -> new IllegalArgumentException("ProductOption not found"));
        }

        CartItem cartItem = CartItem.create(product, productOption, request.quantity());
        return cartItemRepository.save(cartItem);
    }

    public CartItem get(Long id) {
        return cartItemRepository.findById(id).orElseThrow();
    }

    public List<CartItem> getAll() {
        return cartItemRepository.findAll();
    }

    public CartItem update(Long id, CartItemRequest request) {
        CartItem cartItem = get(id);

        if (request.productOptionId() != null) {
            ProductOption productOption = productOptionRepository.findById(request.productOptionId())
                    .orElseThrow(() -> new IllegalArgumentException("ProductOption not found"));
            cartItem.updateProductOption(productOption);
        }

        cartItem.update(request.quantity());
        return cartItemRepository.save(cartItem);
    }

    public void delete(Long id) {
        CartItem cartItem = get(id);
        cartItem.delete();
        cartItemRepository.save(cartItem);
    }
}
