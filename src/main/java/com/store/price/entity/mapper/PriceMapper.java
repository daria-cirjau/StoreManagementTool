package com.store.price.entity.mapper;

import com.store.price.entity.PriceHistory;
import com.store.price.entity.dto.PriceHistoryDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class PriceMapper {

    public PriceHistory toEntity(PriceHistoryDTO dto, Long oldPrice, UUID productId) {
        if (dto == null) {
            return null;
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = auth.getName();

        return PriceHistory.builder()
                .id(dto.id())
                .productId(productId)
                .oldPrice(oldPrice)
                .newPrice(dto.newPrice())
                .changedBy(currentUser)
                .reason(dto.reason())
                .changedAt(new Date())
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
