package com.store.category.controller;

import com.github.fge.jsonpatch.JsonPatch;
import com.store.category.entity.dto.CategoryDTO;
import com.store.category.service.CategoryService;
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
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO dto) {
        CategoryDTO savedObject = categoryService.createCategory(dto);
        return ResponseEntity.created(URI.create("/categories/" + savedObject.id())).body(savedObject);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable UUID id) {
        return ResponseEntity.ok(categoryService.getCategory(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<String> patchCategory(@PathVariable UUID id, @RequestBody JsonPatch patch) {
        categoryService.patchCategory(id, patch);
        return ResponseEntity.ok("Category updated successfully");
    }

    @PatchMapping("/{id}/inactivate")
    public ResponseEntity<String> inactivateCategory(@PathVariable UUID id) {
        categoryService.inactivateCategory(id);
        return ResponseEntity.ok("Category was set to inactive");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.noContent().build();
    }
}
