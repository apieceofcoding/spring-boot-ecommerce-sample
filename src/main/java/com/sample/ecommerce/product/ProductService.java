package com.sample.ecommerce.product;

import com.sample.ecommerce.api.product.ProductRequest;
import com.sample.ecommerce.storage.MinioStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final MinioStorage minioStorage;

    public Product create(ProductRequest request) {
        Product product = Product.create(
                request.title(),
                request.listPrice(),
                request.discountPrice()
        );

        return productRepository.save(product);
    }

    public Product get(Long id) {
        return productRepository.findById(id).orElseThrow();
    }

    public Product update(Long id, ProductRequest request) {
        Product product = get(id);
        product.update(
                request.title(),
                request.listPrice(),
                request.discountPrice()
        );
        return productRepository.save(product);
    }

    public void delete(Long id) {
        Product product = get(id);
        product.delete();
        productRepository.save(product);
    }

    public Product uploadThumbnail(Long id, MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("File must be an image");
        }

        Product product = get(id);

        if (product.getThumbnailUrl() != null && !product.getThumbnailUrl().isBlank()) {
            minioStorage.deleteFile(product.getThumbnailUrl());
        }

        String thumbnailUrl = minioStorage.uploadFile(file, "products/thumbnails");
        product.updateThumbnailUrl(thumbnailUrl);

        return productRepository.save(product);
    }

    public Product deleteThumbnail(Long id) {
        Product product = get(id);

        if (product.getThumbnailUrl() == null || product.getThumbnailUrl().isBlank()) {
            throw new IllegalStateException("Product has no thumbnail to delete");
        }

        minioStorage.deleteFile(product.getThumbnailUrl());
        product.updateThumbnailUrl(null);

        return productRepository.save(product);
    }
}
