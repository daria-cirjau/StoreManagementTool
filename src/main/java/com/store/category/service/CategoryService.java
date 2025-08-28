package com.store.category.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.store.category.entity.Category;
import com.store.category.entity.dto.CategoryDTO;
import com.store.category.entity.mapper.CategoryMapper;
import com.store.category.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final ObjectMapper objectMapper;

    public CategoryService(CategoryRepository categoryRepository,
                           CategoryMapper categoryMapper,
                           ObjectMapper objectMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public CategoryDTO createCategory(CategoryDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Category payload is required");
        }
        Category category = categoryMapper.toEntity(dto);
        if (category.getIsActive() == null) {
            category.setIsActive(true);
        }
        Category saved = categoryRepository.save(category);
        return categoryMapper.toDto(saved);
    }

    public CategoryDTO getCategory(UUID id) {
        Category entity = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category with id " + id + " not found"));
        return categoryMapper.toDto(entity);
    }

    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Transactional
    public void patchCategory(UUID id, JsonPatch patch) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category with id " + id + " not found"));

        CategoryDTO dto = categoryMapper.toDto(category);
        JsonNode categoryJsonNode = objectMapper.valueToTree(dto);

        try {
            JsonNode patchedCategory = patch.apply(categoryJsonNode);
            dto = objectMapper.treeToValue(patchedCategory, CategoryDTO.class);

            categoryMapper.applyPatchToEntity(dto, category);

            categoryRepository.save(category);
        } catch (JsonPatchException | JsonProcessingException e) {
            throw new org.springframework.web.server.ResponseStatusException(
                    org.springframework.http.HttpStatus.BAD_REQUEST, "Invalid JSON Patch: " + e.getMessage(), e);
        }
    }

    @Transactional
    public void inactivateCategory(UUID id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category with id " + id + " not found"));
        category.setIsActive(false);
        categoryRepository.save(category);
    }

    @Transactional
    public void deleteCategoryById(UUID id) {
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException("Category with id " + id + " not found");
        }
        categoryRepository.deleteById(id);
    }
}
