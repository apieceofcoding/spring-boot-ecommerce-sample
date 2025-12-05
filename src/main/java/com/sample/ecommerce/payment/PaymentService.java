package com.sample.ecommerce.payment;

import com.sample.ecommerce.order.Order;
import com.sample.ecommerce.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final TossPaymentsClient tossPaymentsClient;

    @Transactional
    public Payment confirm(String paymentKey, String orderId, Long amount) {
        // 1. Order 조회
        Order order = orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId));

        // 2. 이미 결제 승인된 주문인지 확인
        if (paymentRepository.findByOrderId(orderId).isPresent()) {
            throw new IllegalStateException("Payment already exists for order: " + orderId);
        }

        // 3. TossPayments API 호출하여 결제 승인
        TossPaymentResult result = tossPaymentsClient.confirm(paymentKey, orderId, amount);

        return switch (result) {
            case TossPaymentResult.Success success -> {
                // 4. Payment 엔티티 생성 및 저장 (성공)
                TossPaymentResponse response = success.response();
                Payment payment = Payment.create(
                        response.paymentKey(),
                        response.orderId(),
                        response.totalAmount(),
                        response.method(),
                        response.approvedAt()
                );
                paymentRepository.save(payment);

                // 5. Order 상태를 PAID로 업데이트
                order.pay();
                orderRepository.save(order);

                yield payment;
            }
            case TossPaymentResult.Failure failure -> {
                // 6. TossPayments API 호출 실패 시 Payment를 FAILED 상태로 저장
                log.error("TossPayments API call failed for orderId: {}, code: {}, message: {}",
                        orderId, failure.code(), failure.message());

                Payment failedPayment = Payment.createFailed(
                        paymentKey,
                        orderId,
                        amount != null ? java.math.BigDecimal.valueOf(amount) : null,
                        failure.getFormattedError()
                );
                paymentRepository.save(failedPayment);

                yield failedPayment;
            }
        };
    }
}
