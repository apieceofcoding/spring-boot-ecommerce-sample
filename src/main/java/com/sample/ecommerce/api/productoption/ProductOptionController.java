package com.sample.ecommerce.api.productoption;

import com.sample.ecommerce.product.ProductOption;
import com.sample.ecommerce.product.ProductOptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProductOptionController {

    private final ProductOptionService productOptionService;

    @PostMapping("/api/v1/products/{productId}/options")
    public ProductOptionResponse create(
            @PathVariable Long productId,
            @RequestBody ProductOptionRequest request
    ) {
        ProductOption option = productOptionService.create(productId, request);
        return ProductOptionResponse.from(option);
    }

    @GetMapping("/api/v1/products/{productId}/options")
    public List<ProductOptionResponse> getByProductId(@PathVariable Long productId) {
        return productOptionService.getByProductId(productId).stream()
                .map(ProductOptionResponse::from)
                .toList();
    }

    @GetMapping("/api/v1/product-options/{id}")
    public ProductOptionResponse get(@PathVariable Long id) {
        ProductOption option = productOptionService.get(id);
        return ProductOptionResponse.from(option);
    }

    @PutMapping("/api/v1/product-options/{id}")
    public ProductOptionResponse update(
            @PathVariable Long id,
            @RequestBody ProductOptionRequest request
    ) {
        ProductOption option = productOptionService.update(id, request);
        return ProductOptionResponse.from(option);
    }

    @DeleteMapping("/api/v1/product-options/{id}")
    public void delete(@PathVariable Long id) {
        productOptionService.delete(id);
    }
}
