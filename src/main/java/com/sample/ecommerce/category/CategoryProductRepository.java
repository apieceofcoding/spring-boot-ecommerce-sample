package com.sample.ecommerce.category;

import com.sample.ecommerce.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryProductRepository extends JpaRepository<CategoryProduct, Long> {

    List<CategoryProduct> findByCategory(Category category);

    List<CategoryProduct> findByProduct(Product product);

    Optional<CategoryProduct> findByCategoryAndProduct(Category category, Product product);

    void deleteByCategoryAndProduct(Category category, Product product);
}
