package com.store.product.controller;

import com.github.fge.jsonpatch.JsonPatch;
import com.store.product.entity.dto.ProductDTO;
import com.store.product.service.ProductService;
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
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO product) {
        ProductDTO savedObject = productService.createProduct(product);
        return ResponseEntity.created(URI.create("/products/" + savedObject.id())).body(savedObject);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable UUID id) {
        return ResponseEntity.ok(productService.getProduct(id));
    }
    @GetMapping("/all")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }
    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
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
