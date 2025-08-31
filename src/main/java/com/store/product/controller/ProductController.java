package com.store.product.controller;

import com.github.fge.jsonpatch.JsonPatch;
import com.store.product.entity.dto.ProductDTO;
import com.store.product.service.ProductService;
import com.store.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {
    ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse> createProduct(@RequestBody ProductDTO product) {
        ProductDTO savedObject = productService.createProduct(product);
        return ResponseEntity
                .created(URI.create("/products/" + savedObject.id()))
                .body(ApiResponse.of("Product created successfully", 201, savedObject));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getProduct(@PathVariable UUID id) {
        ProductDTO dto = productService.getProduct(id);
        return ResponseEntity.ok(ApiResponse.ofData(dto, 200));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts() {
        List<ProductDTO> list = productService.getAllProducts();
        return ResponseEntity.ok(ApiResponse.ofData(list, 200));
    }

    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<ApiResponse> patchProduct(@PathVariable UUID id, @RequestBody JsonPatch patch) {
        productService.patchProduct(id, patch);
        return ResponseEntity.ok(ApiResponse.ofMessage("Product updated successfully", 200));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable UUID id) {
        productService.deleteProductById(id);
        return ResponseEntity.ok(ApiResponse.ofMessage("Product deleted successfully", 200));
    }

    @GetMapping(params = "categoryName")
    public ResponseEntity<ApiResponse> getByCategoryName(@RequestParam String categoryName) {
        List<ProductDTO> list = productService.getProductsByCategoryName(categoryName);
        return ResponseEntity.ok(ApiResponse.ofData(list, 200));
    }

    @GetMapping(params = "categoryId")
    public ResponseEntity<ApiResponse> getByCategoryId(@RequestParam UUID categoryId) {
        List<ProductDTO> list = productService.getProductsByCategoryId(categoryId);
        return ResponseEntity.ok(ApiResponse.ofData(list, 200));
    }
}
