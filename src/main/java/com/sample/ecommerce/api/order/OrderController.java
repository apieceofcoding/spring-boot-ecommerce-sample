package com.sample.ecommerce.api.order;

import com.sample.ecommerce.order.Order;
import com.sample.ecommerce.order.OrderProduct;
import com.sample.ecommerce.order.OrderProductRepository;
import com.sample.ecommerce.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderService orderService;
    private final OrderProductRepository orderProductRepository;

    @PostMapping("/api/v1/orders")
    public OrderResponse create(@RequestBody OrderRequest request) {
        Order order = orderService.create(request.cartItemIds());
        List<OrderProduct> orderProducts = orderProductRepository.findByOrder(order);
        List<OrderProductResponse> orderProductResponses = orderProducts.stream()
                .map(OrderProductResponse::from)
                .toList();
        return OrderResponse.from(order, orderProductResponses);
    }

    @GetMapping("/api/v1/orders/{id}")
    public OrderResponse get(@PathVariable Long id) {
        Order order = orderService.get(id);
        List<OrderProduct> orderProducts = orderProductRepository.findByOrder(order);
        List<OrderProductResponse> orderProductResponses = orderProducts.stream()
                .map(OrderProductResponse::from)
                .toList();
        return OrderResponse.from(order, orderProductResponses);
    }

    @PatchMapping("/api/v1/orders/{id}/paid")
    public OrderResponse pay(@PathVariable Long id) {
        Order order = orderService.pay(id);
        List<OrderProduct> orderProducts = orderProductRepository.findByOrder(order);
        List<OrderProductResponse> orderProductResponses = orderProducts.stream()
                .map(OrderProductResponse::from)
                .toList();
        return OrderResponse.from(order, orderProductResponses);
    }

    @PatchMapping("/api/v1/orders/{id}/complete")
    public OrderResponse complete(@PathVariable Long id) {
        Order order = orderService.complete(id);
        List<OrderProduct> orderProducts = orderProductRepository.findByOrder(order);
        List<OrderProductResponse> orderProductResponses = orderProducts.stream()
                .map(OrderProductResponse::from)
                .toList();
        return OrderResponse.from(order, orderProductResponses);
    }
}
