package com.store.category.entity.dto;

import lombok.Builder;

import java.util.Date;
import java.util.UUID;

@Builder
public record CategoryDTO(
        UUID id,
        UUID parentId,
        String name,
        String description,
        Boolean isActive,
        Date createdAt,
        Date updatedAt
) {
}
