package com.store.price.entity.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;
import java.util.UUID;

public record PriceHistoryDTO(
        UUID id,
        @NotBlank(message = "Product id is required")
        UUID productId,
        @Min(value = 0, message = "Price must be non-negative")
        Long oldPrice,
        @Min(value = 0, message = "Price must be non-negative")
        Long newPrice,
        @NotBlank(message = "ChangedBy field is required")
        String changedBy,
        String reason,
        Date changedAt
) {
}
