package com.sample.ecommerce.order;

import com.sample.ecommerce.cart.CartItem;
import com.sample.ecommerce.cart.CartItemRepository;
import com.sample.ecommerce.product.Product;
import com.sample.ecommerce.product.ProductOption;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final CartItemRepository cartItemRepository;

    @Transactional
    public Order create(List<Long> cartItemIds) {
        if (cartItemIds == null || cartItemIds.isEmpty()) {
            throw new IllegalArgumentException("Cart item IDs cannot be null or empty");
        }

        List<CartItem> cartItems = cartItemRepository.findAllById(cartItemIds);
        if (cartItems.size() != cartItemIds.size()) {
            throw new IllegalArgumentException("Some cart items not found");
        }

        Order order = Order.create();
        orderRepository.save(order);

        cartItems.forEach(cartItem -> {
            Product product = cartItem.getProduct();
            ProductOption productOption = cartItem.getProductOption();

            BigDecimal productPrice = calculateProductPrice(product, productOption);

            OrderProduct orderProduct = OrderProduct.create(
                    order,
                    product.getId(),
                    product.getTitle(),
                    productOption != null ? productOption.getId() : null,
                    productPrice,
                    productOption != null ? productOption.getName() : null,
                    cartItem.getQuantity()
            );
            orderProductRepository.save(orderProduct);
        });

        return order;
    }

    public Order get(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
    }

    @Transactional
    public Order pay(Long id) {
        Order order = get(id);
        order.pay();
        return orderRepository.save(order);
    }

    @Transactional
    public Order complete(Long id) {
        Order order = get(id);
        order.complete();
        return orderRepository.save(order);
    }

    private BigDecimal calculateProductPrice(Product product, ProductOption productOption) {
        BigDecimal basePrice = product.getDiscountPrice() != null
                ? product.getDiscountPrice()
                : product.getListPrice();

        if (productOption != null) {
            return basePrice.add(productOption.getPriceDiff());
        }

        return basePrice;
    }
}
