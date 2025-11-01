package com.sample.ecommerce.api.product;

import com.sample.ecommerce.product.Product;
import com.sample.ecommerce.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ProductController {

    private final ProductService productService;

    @PostMapping("/api/v1/products")
    public ProductResponse create(@RequestBody ProductRequest request) {
        Product product = productService.create(request);
        return ProductResponse.from(product);
    }

    @GetMapping("/api/v1/products/{id}")
    public ProductResponse get(@PathVariable Long id) {
        Product product = productService.get(id);
        return ProductResponse.from(product);
    }
}
