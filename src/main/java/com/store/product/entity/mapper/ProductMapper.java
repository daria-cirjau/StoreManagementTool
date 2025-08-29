package com.store.product.entity.mapper;

import com.store.product.entity.Product;
import com.store.product.entity.dto.ProductDTO;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toEntity(ProductDTO dto) {
        if (dto == null) {
            return null;
        }

        return Product.builder()
                .name(dto.name())
                .price(dto.price())
                .currency(dto.currency())
                .quantity(dto.quantity())
                .category(dto.category())
                .build();
    }

    public ProductDTO toDto(Product entity) {
        if (entity == null) {
            return null;
        }

        return ProductDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .price(entity.getPrice())
                .currency(entity.getCurrency())
                .quantity(entity.getQuantity())
                .category(entity.getCategory())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public void applyPatchToEntity(ProductDTO dto, Product entity) {
        if (dto.name() != null) {
            entity.setName(dto.name());
        }
        if (dto.currency() != null) {
            entity.setCurrency(dto.currency());
        }
        if (dto.quantity() != null) {
            entity.setQuantity(dto.quantity());
        }
        if (dto.category() != null) {
            entity.setCategory(dto.category());
        }
        if (dto.price() != null && !dto.price().equals(entity.getPrice())) {
            throw new IllegalArgumentException("Price update not permitted.");
        }
    }

}
