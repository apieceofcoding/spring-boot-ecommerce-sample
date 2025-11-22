package com.sample.ecommerce.category;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Table(name = "categories")
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean deleted = false;

    public void update(String name) {
        if (name != null && !name.isBlank()) {
            this.name = name;
        }
    }

    public void delete() {
        if (deleted) {
            throw new IllegalStateException("Category is already deleted");
        }
        deleted = true;
    }

    public static Category create(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }

        Category category = new Category();
        category.name = name;
        return category;
    }
}
