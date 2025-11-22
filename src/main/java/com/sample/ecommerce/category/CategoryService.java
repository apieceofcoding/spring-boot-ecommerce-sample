package com.sample.ecommerce.category;

import com.sample.ecommerce.api.category.CategoryRequest;
import com.sample.ecommerce.product.Product;
import com.sample.ecommerce.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryProductRepository categoryProductRepository;
    private final ProductRepository productRepository;

    public Category create(CategoryRequest request) {
        Category category = Category.create(request.name());
        return categoryRepository.save(category);
    }

    public Category get(Long id) {
        return categoryRepository.findById(id).orElseThrow();
    }

    public List<Category> getAll() {
        return categoryRepository.findByDeletedFalse();
    }

    public Category update(Long id, CategoryRequest request) {
        Category category = get(id);
        category.update(request.name());
        return categoryRepository.save(category);
    }

    public void delete(Long id) {
        Category category = get(id);
        category.delete();
        categoryRepository.save(category);
    }

    @Transactional
    public void addProduct(Long categoryId, Long productId) {
        Category category = get(categoryId);
        Product product = productRepository.findById(productId).orElseThrow();

        if (categoryProductRepository.findByCategoryAndProduct(category, product).isPresent()) {
            throw new IllegalStateException("Product is already in this category");
        }

        CategoryProduct categoryProduct = CategoryProduct.create(category, product);
        categoryProductRepository.save(categoryProduct);
    }

    @Transactional
    public void removeProduct(Long categoryId, Long productId) {
        Category category = get(categoryId);
        Product product = productRepository.findById(productId).orElseThrow();

        categoryProductRepository.deleteByCategoryAndProduct(category, product);
    }

    public List<Product> getProducts(Long categoryId) {
        Category category = get(categoryId);
        return categoryProductRepository.findByCategory(category).stream()
                .map(CategoryProduct::getProduct)
                .toList();
    }

    public List<Category> getCategoriesByProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow();
        return categoryProductRepository.findByProduct(product).stream()
                .map(CategoryProduct::getCategory)
                .toList();
    }
}
