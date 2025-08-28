package com.store.product.repository;

import com.store.category.entity.Category;
import com.store.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface  ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findByCategory(Category category);
    List<Product> findByCategoryId(UUID categoryId);
}
