package com.sample.ecommerce.order;

import com.sample.ecommerce.cart.CartItem;
import com.sample.ecommerce.cart.CartItemRepository;
import com.sample.ecommerce.product.Product;
import com.sample.ecommerce.product.ProductOption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderProductRepository orderProductRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @InjectMocks
    private OrderService orderService;

    private Product product;
    private ProductOption productOption;
    private CartItem cartItem1;
    private CartItem cartItem2;

    @BeforeEach
    void setUp() {
        // Product mock
        product = mock(Product.class);
        when(product.getId()).thenReturn(1L);
        when(product.getTitle()).thenReturn("MacBook Air M3");
        when(product.getListPrice()).thenReturn(new BigDecimal("1890000"));
        when(product.getDiscountPrice()).thenReturn(new BigDecimal("1690000"));

        // ProductOption mock
        productOption = mock(ProductOption.class);
        when(productOption.getId()).thenReturn(1L);
        when(productOption.getPriceDiff()).thenReturn(new BigDecimal("100000"));

        // CartItem mock
        cartItem1 = mock(CartItem.class);
        when(cartItem1.getProduct()).thenReturn(product);
        when(cartItem1.getProductOption()).thenReturn(null);
        when(cartItem1.getQuantity()).thenReturn(2);

        cartItem2 = mock(CartItem.class);
        when(cartItem2.getProduct()).thenReturn(product);
        when(cartItem2.getProductOption()).thenReturn(productOption);
        when(cartItem2.getQuantity()).thenReturn(1);
    }

    @Test
    @DisplayName("여러 CartItem으로 주문을 생성할 수 있다")
    void createOrderWithMultipleCartItems() {
        // given
        List<Long> cartItemIds = Arrays.asList(1L, 2L);
        List<CartItem> cartItems = Arrays.asList(cartItem1, cartItem2);

        when(cartItemRepository.findAllById(cartItemIds)).thenReturn(cartItems);
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(orderProductRepository.save(any(OrderProduct.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        Order order = orderService.create(cartItemIds);

        // then
        assertNotNull(order);
        assertEquals(OrderStatus.CREATED, order.getStatus());
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(orderProductRepository, times(2)).save(any(OrderProduct.class));
    }

    @Test
    @DisplayName("단일 CartItem으로 주문을 생성할 수 있다")
    void createOrderWithSingleCartItem() {
        // given
        List<Long> cartItemIds = Collections.singletonList(1L);
        List<CartItem> cartItems = Collections.singletonList(cartItem1);

        when(cartItemRepository.findAllById(cartItemIds)).thenReturn(cartItems);
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(orderProductRepository.save(any(OrderProduct.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        Order order = orderService.create(cartItemIds);

        // then
        assertNotNull(order);
        assertEquals(OrderStatus.CREATED, order.getStatus());
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(orderProductRepository, times(1)).save(any(OrderProduct.class));
    }

    @Test
    @DisplayName("CartItem ID 리스트가 null이면 주문을 생성할 수 없다")
    void createOrderWithNullCartItemIds() {
        // when & then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> orderService.create(null));
        assertEquals("Cart item IDs cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("CartItem ID 리스트가 비어있으면 주문을 생성할 수 없다")
    void createOrderWithEmptyCartItemIds() {
        // given
        List<Long> cartItemIds = Collections.emptyList();

        // when & then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> orderService.create(cartItemIds));
        assertEquals("Cart item IDs cannot be null or empty", exception.getMessage());
    }

    @Test
    @DisplayName("일부 CartItem이 존재하지 않으면 주문을 생성할 수 없다")
    void createOrderWithNonExistentCartItems() {
        // given
        List<Long> cartItemIds = Arrays.asList(1L, 2L, 3L);
        List<CartItem> cartItems = Arrays.asList(cartItem1, cartItem2); // 2개만 존재

        when(cartItemRepository.findAllById(cartItemIds)).thenReturn(cartItems);

        // when & then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> orderService.create(cartItemIds));
        assertEquals("Some cart items not found", exception.getMessage());
    }

    @Test
    @DisplayName("주문을 조회할 수 있다")
    void getOrder() {
        // given
        Order order = Order.create();
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        // when
        Order foundOrder = orderService.get(1L);

        // then
        assertNotNull(foundOrder);
        assertEquals(order, foundOrder);
    }

    @Test
    @DisplayName("존재하지 않는 주문을 조회하면 예외가 발생한다")
    void getOrderNotFound() {
        // given
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        // when & then
        assertThrows(IllegalArgumentException.class,
                () -> orderService.get(1L));
    }

    @Test
    @DisplayName("주문을 결제 완료 상태로 변경할 수 있다")
    void markOrderAsPaid() {
        // given
        Order order = Order.create();
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        Order paidOrder = orderService.pay(1L);

        // then
        assertEquals(OrderStatus.PAID, paidOrder.getStatus());
        assertNotNull(paidOrder.getPaidAt());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    @DisplayName("주문을 완료 상태로 변경할 수 있다")
    void markOrderAsComplete() {
        // given
        Order order = Order.create();
        order.pay();
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        Order completedOrder = orderService.complete(1L);

        // then
        assertEquals(OrderStatus.COMPLETE, completedOrder.getStatus());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    @DisplayName("CREATED 상태가 아닌 주문은 결제 완료로 변경할 수 없다")
    void payFromInvalidStatus() {
        // given
        Order order = Order.create();
        order.pay();
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        // when & then
        assertThrows(IllegalStateException.class,
                () -> orderService.pay(1L));
    }

    @Test
    @DisplayName("PAID 상태가 아닌 주문은 완료로 변경할 수 없다")
    void completeFromInvalidStatus() {
        // given
        Order order = Order.create();
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        // when & then
        assertThrows(IllegalStateException.class,
                () -> orderService.complete(1L));
    }
}
