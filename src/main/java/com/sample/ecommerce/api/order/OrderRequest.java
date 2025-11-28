package com.sample.ecommerce.api.order;

import java.util.List;

public record OrderRequest(
        List<Long> cartItemIds
) {
}
