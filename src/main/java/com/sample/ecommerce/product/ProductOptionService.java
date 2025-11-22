package com.sample.ecommerce.product;

import com.sample.ecommerce.api.productoption.ProductOptionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductOptionService {

    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;

    public ProductOption create(Long productId, ProductOptionRequest request) {
        Product product = productRepository.findById(productId).orElseThrow();
        ProductOption option = ProductOption.create(
                product,
                request.name(),
                request.priceDiff(),
                request.stock()
        );
        return productOptionRepository.save(option);
    }

    public List<ProductOption> getByProductId(Long productId) {
        return productOptionRepository.findByProductId(productId);
    }

    public ProductOption get(Long id) {
        return productOptionRepository.findById(id).orElseThrow();
    }

    public ProductOption update(Long id, ProductOptionRequest request) {
        ProductOption option = get(id);
        option.update(
                request.name(),
                request.priceDiff(),
                request.stock()
        );
        return productOptionRepository.save(option);
    }

    public void delete(Long id) {
        ProductOption option = get(id);
        option.delete();
        productOptionRepository.save(option);
    }
}
