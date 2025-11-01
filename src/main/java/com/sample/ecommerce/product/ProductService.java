package com.sample.ecommerce.product;

import com.sample.ecommerce.api.product.ProductRequest;
import com.sample.ecommerce.api.product.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public Product create(ProductRequest request) {

        Product product = Product.create(
                request.title(),
                request.listPrice(),
                request.discountPrice(),
                request.thumbnailUrl()
        );

        return productRepository.save(product);
    }

    public Product get(Long id) {
        return productRepository.findById(id).orElseThrow();
    }
}
