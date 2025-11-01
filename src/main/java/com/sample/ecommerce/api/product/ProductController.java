package com.sample.ecommerce.api.product;

import com.sample.ecommerce.product.Product;
import com.sample.ecommerce.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PutMapping("/api/v1/products/{id}")
    public ProductResponse update(@PathVariable Long id, @RequestBody ProductRequest request) {
        Product product = productService.update(id, request);
        return ProductResponse.from(product);
    }

    @DeleteMapping("/api/v1/products/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @PostMapping("/api/v1/products/{id}/thumbnail")
    public ProductResponse uploadThumbnail(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file
    ) {
        Product product = productService.uploadThumbnail(id, file);
        return ProductResponse.from(product);
    }

    @DeleteMapping("/api/v1/products/{id}/thumbnail")
    public ProductResponse deleteThumbnail(@PathVariable Long id) {
        Product product = productService.deleteThumbnail(id);
        return ProductResponse.from(product);
    }
}
