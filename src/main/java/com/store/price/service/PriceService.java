package com.store.price.service;

import com.store.price.entity.PriceHistory;
import com.store.price.entity.dto.PriceHistoryDTO;
import com.store.price.entity.mapper.PriceMapper;
import com.store.price.repository.PriceRepository;
import com.store.product.entity.Product;
import com.store.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PriceService {

    private final ProductRepository productRepository;
    private final PriceRepository priceRepository;
    private final PriceMapper priceMapper;

    public PriceService(ProductRepository productRepository,
                        PriceRepository priceRepository,
                        PriceMapper priceMapper) {
        this.productRepository = productRepository;
        this.priceRepository = priceRepository;
        this.priceMapper = priceMapper;
    }

    @Transactional
    public void updatePrice(UUID productId, PriceHistoryDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Price payload is required");
        }
        if (dto.newPrice() == null || dto.newPrice() < 0) {
            throw new IllegalArgumentException("New price must be non-negative");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product " + productId + " not found"));

        Long oldPrice = product.getPrice();
        Long newPrice = dto.newPrice();

        int updatedRows = productRepository.updatePrice(productId, newPrice);
        if (updatedRows == 0) {
            throw new DataIntegrityViolationException("Product " + productId + " price could not be updated.");
        }

        PriceHistory history = priceMapper.toEntity(dto, oldPrice);
        priceRepository.save(history);
    }

    @Transactional
    public List<PriceHistoryDTO> getPriceHistory(UUID productId) {
        List<PriceHistory> history = priceRepository.findByProductId(productId);
        if (history.isEmpty()) {
            throw new EntityNotFoundException("No price history for product " + productId);
        }
        return history.stream().map(priceMapper::toDto).toList();
    }
}
