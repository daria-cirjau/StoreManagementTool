package com.store.category.controller;

import com.github.fge.jsonpatch.JsonPatch;
import com.store.category.entity.dto.CategoryDTO;
import com.store.category.service.CategoryService;
import com.store.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;
    public CategoryController(CategoryService categoryService) { this.categoryService = categoryService; }

    @PostMapping
    public ResponseEntity<ApiResponse> createCategory(@RequestBody CategoryDTO dto) {
        CategoryDTO savedObject = categoryService.createCategory(dto);
        return ResponseEntity
                .created(URI.create("/categories/" + savedObject.id()))
                .body(ApiResponse.of("Category created successfully", 201, savedObject));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCategory(@PathVariable UUID id) {
        CategoryDTO dto = categoryService.getCategory(id);
        return ResponseEntity.ok(ApiResponse.ofData(dto, 200));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories() {
        List<CategoryDTO> list = categoryService.getAllCategories();
        return ResponseEntity.ok(ApiResponse.ofData(list, 200));
    }

    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<ApiResponse> patchCategory(@PathVariable UUID id, @RequestBody JsonPatch patch) {
        categoryService.patchCategory(id, patch);
        return ResponseEntity.ok(ApiResponse.ofMessage("Category updated successfully", 200));
    }

    @PatchMapping("/{id}/inactivate")
    public ResponseEntity<ApiResponse> inactivateCategory(@PathVariable UUID id) {
        categoryService.inactivateCategory(id);
        return ResponseEntity.ok(ApiResponse.ofMessage("Category was set to inactive", 200));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.ok(ApiResponse.ofMessage("Category deleted successfully", 200));
    }
}
