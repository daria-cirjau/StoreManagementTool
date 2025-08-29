package com.store.price.entity.mapper;

import com.store.price.entity.PriceHistory;
import com.store.price.entity.dto.PriceHistoryDTO;
import org.springframework.stereotype.Component;

@Component
public class PriceMapper {

    public PriceHistory toEntity(PriceHistoryDTO dto, Long oldPrice) {
        if (dto == null) return null;

        return PriceHistory.builder()
                .id(dto.id())
                .productId(dto.productId())
                .oldPrice(oldPrice)
                .newPrice(dto.newPrice())
                .changedBy(dto.changedBy())
                .reason(dto.reason())
                .changedAt(dto.changedAt())
                .build();
    }

    public PriceHistoryDTO toDto(PriceHistory entity) {
        if (entity == null) return null;

        return new PriceHistoryDTO(
                entity.getId(),
                entity.getProductId(),
                entity.getOldPrice(),
                entity.getNewPrice(),
                entity.getChangedBy(),
                entity.getReason(),
                entity.getChangedAt()
        );
    }
}
