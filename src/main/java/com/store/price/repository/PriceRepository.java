package com.store.price.repository;

import com.store.price.entity.PriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PriceRepository extends JpaRepository<PriceHistory, UUID> {
    List<PriceHistory> findByProductId(UUID productId);
}
