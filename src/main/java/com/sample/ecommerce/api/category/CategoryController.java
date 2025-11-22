package com.sample.ecommerce.api.category;

import com.sample.ecommerce.api.product.ProductResponse;
import com.sample.ecommerce.category.Category;
import com.sample.ecommerce.category.CategoryService;
import com.sample.ecommerce.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/api/v1/categories")
    public CategoryResponse create(@RequestBody CategoryRequest request) {
        Category category = categoryService.create(request);
        return CategoryResponse.from(category);
    }

    @GetMapping("/api/v1/categories/{id}")
    public CategoryResponse get(@PathVariable Long id) {
        Category category = categoryService.get(id);
        return CategoryResponse.from(category);
    }

    @GetMapping("/api/v1/categories")
    public List<CategoryResponse> getAll() {
        return categoryService.getAll().stream()
                .map(CategoryResponse::from)
                .toList();
    }

    @PutMapping("/api/v1/categories/{id}")
    public CategoryResponse update(@PathVariable Long id, @RequestBody CategoryRequest request) {
        Category category = categoryService.update(id, request);
        return CategoryResponse.from(category);
    }

    @DeleteMapping("/api/v1/categories/{id}")
    public void delete(@PathVariable Long id) {
        categoryService.delete(id);
    }

    @PostMapping("/api/v1/categories/{categoryId}/products/{productId}")
    public void addProduct(@PathVariable Long categoryId, @PathVariable Long productId) {
        categoryService.addProduct(categoryId, productId);
    }

    @DeleteMapping("/api/v1/categories/{categoryId}/products/{productId}")
    public void removeProduct(@PathVariable Long categoryId, @PathVariable Long productId) {
        categoryService.removeProduct(categoryId, productId);
    }

    @GetMapping("/api/v1/categories/{categoryId}/products")
    public List<ProductResponse> getProducts(@PathVariable Long categoryId) {
        List<Product> products = categoryService.getProducts(categoryId);
        return products.stream()
                .map(ProductResponse::from)
                .toList();
    }

    @GetMapping("/api/v1/products/{productId}/categories")
    public List<CategoryResponse> getCategoriesByProduct(@PathVariable Long productId) {
        List<Category> categories = categoryService.getCategoriesByProduct(productId);
        return categories.stream()
                .map(CategoryResponse::from)
                .toList();
    }
}
