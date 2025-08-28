package com.store.category.entity.mapper;

import com.store.category.entity.Category;
import com.store.category.entity.dto.CategoryDTO;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toEntity(CategoryDTO dto) {
        if (dto == null) {
            return null;
        }

        return Category.builder()
                .parentId(dto.parentId())
                .name(dto.name())
                .description(dto.description())
                .isActive(dto.isActive())
                .build();
    }

    public CategoryDTO toDto(Category entity) {
        if (entity == null) {
            return null;
        }

        return CategoryDTO.builder()
                .id(entity.getId())
                .parentId(entity.getParentId())
                .name(entity.getName())
                .description(entity.getDescription())
                .isActive(entity.getIsActive())
                 .createdAt(entity.getCreatedAt())
                 .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public void applyPatchToEntity(CategoryDTO dto, Category entity) {
        if (dto.name() != null)          {
            entity.setName(dto.name());
        }
        if (dto.description() != null)   {
            entity.setDescription(dto.description());
        }
        if (dto.isActive() != null)      {
            entity.setIsActive(dto.isActive());
        }
    }
}
