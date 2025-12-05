package com.sample.ecommerce.payment;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class TossPaymentsClient {

    private final TossPaymentsProperty tossPaymentsProperty;
    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    public TossPaymentResult confirm(String paymentKey, String orderId, Long amount) {
        String url = tossPaymentsProperty.baseUrl() + "/v1/payments/confirm";

        Map<String, Object> body = Map.of(
                "paymentKey", paymentKey,
                "orderId", orderId,
                "amount", amount
        );

        try {
            TossPaymentResponse response = restClient.post()
                    .uri(url)
                    .header("Authorization", "Basic " + encodeSecretKey())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(body)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, (request, clientResponse) -> {
                        String responseBody = new String(clientResponse.getBody().readAllBytes(), StandardCharsets.UTF_8);
                        log.error("TossPayments API error response: {}", responseBody);

                        TossPaymentErrorResponse errorResponse = objectMapper.readValue(responseBody, TossPaymentErrorResponse.class);
                        throw new TossPaymentException(errorResponse.code(), errorResponse.message());
                    })
                    .body(TossPaymentResponse.class);

            if (response == null) {
                return new TossPaymentResult.Failure("EMPTY_RESPONSE", "TossPayments API returned empty response");
            }

            return new TossPaymentResult.Success(response);
        } catch (TossPaymentException e) {
            return new TossPaymentResult.Failure(e.getCode(), e.getErrorMessage());
        } catch (Exception e) {
            return new TossPaymentResult.Failure("UNKNOWN", e.getMessage());
        }
    }

    private String encodeSecretKey() {
        String credentials = tossPaymentsProperty.secretKey() + ":";
        return Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
    }
}
