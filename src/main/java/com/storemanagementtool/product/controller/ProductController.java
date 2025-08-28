package com.storemanagementtool.product.controller;

import com.github.fge.jsonpatch.JsonPatch;
import com.storemanagementtool.product.entity.dto.ProductDTO;
import com.storemanagementtool.product.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> createProduct(@RequestBody ProductDTO product) {
        productService.createProduct(product);
        return ResponseEntity.ok("Product created successfully");
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable UUID id) {
        return ResponseEntity.ok(productService.getProduct(id));
    }
    @GetMapping("/all")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }
    @PatchMapping("/{id}")
    public ResponseEntity<String> patchProduct(@PathVariable UUID id, @RequestBody JsonPatch patch) {
        productService.patchProduct(id, patch);
        return ResponseEntity.ok("Product updated successfully");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable UUID id) {
        productService.deleteProductById(id);
        return ResponseEntity.ok("Product deleted successfully");
    }
}
