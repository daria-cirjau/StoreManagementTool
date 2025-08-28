package com.storemanagementtool.product.entity.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.Date;
import java.util.UUID;

@Builder
public record ProductDTO(
        @NotBlank(message = "Name is required")
        String name,
        @Min(value = 0, message = "Price must be non-negative")
        Long price,
        @NotBlank(message = "Currency is required")
        String currency,
        @Min(value = 0, message = "Quantity must be greater or equal to 0")
        Integer quantity,
        String category,
        UUID id,
        Date createdAt,
        Date updatedAt
) { }
