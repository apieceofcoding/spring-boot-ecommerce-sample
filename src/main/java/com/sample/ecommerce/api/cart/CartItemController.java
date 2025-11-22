package com.sample.ecommerce.api.cart;

import com.sample.ecommerce.cart.CartItem;
import com.sample.ecommerce.cart.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CartItemController {

    private final CartItemService cartItemService;

    @PostMapping("/api/v1/cart-items")
    public CartItemResponse create(@RequestBody CartItemRequest request) {
        CartItem cartItem = cartItemService.create(request);
        return CartItemResponse.from(cartItem);
    }

    @GetMapping("/api/v1/cart-items/{id}")
    public CartItemResponse get(@PathVariable Long id) {
        CartItem cartItem = cartItemService.get(id);
        return CartItemResponse.from(cartItem);
    }

    @GetMapping("/api/v1/cart-items")
    public List<CartItemResponse> getAll() {
        return cartItemService.getAll().stream()
                .map(CartItemResponse::from)
                .toList();
    }

    @PutMapping("/api/v1/cart-items/{id}")
    public CartItemResponse update(@PathVariable Long id, @RequestBody CartItemRequest request) {
        CartItem cartItem = cartItemService.update(id, request);
        return CartItemResponse.from(cartItem);
    }

    @DeleteMapping("/api/v1/cart-items/{id}")
    public void delete(@PathVariable Long id) {
        cartItemService.delete(id);
    }
}
