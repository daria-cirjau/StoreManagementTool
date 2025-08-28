package com.store.product.repository;

import com.store.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface  ProductRepository extends JpaRepository<Product, UUID> {
}
