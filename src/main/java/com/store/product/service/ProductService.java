package com.store.product.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.store.product.entity.Product;
import com.store.product.entity.dto.ProductDTO;
import com.store.product.entity.mapper.ProductMapper;
import com.store.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ObjectMapper objectMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper, ObjectMapper objectMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO) {
        if (productDTO == null) {
            throw new IllegalArgumentException("Product payload is required");
        }
        Product product = productMapper.toEntity(productDTO);
        product = Product.builder()
                .name(product.getName())
                .price(product.getPrice())
                .currency(product.getCurrency())
                .quantity(product.getQuantity())
                .category(product.getCategory())
                .build();

        productRepository.save(product);

        return productMapper.toDto(product);
    }

    public ProductDTO getProduct(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product with id " + id + " not found"));
        return productMapper.toDto(product);
    }

    @Transactional
    public void patchProduct(UUID id, JsonPatch patch) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product with id " + id + " not found"));
        ProductDTO productDTO = productMapper.toDto(product);
        JsonNode productJsonNode = objectMapper.valueToTree(productDTO);
        try {
            JsonNode patchedNode = patch.apply(productJsonNode);
            productDTO = objectMapper.treeToValue(patchedNode, ProductDTO.class);
            productMapper.applyPatchToEntity(productDTO,product);

            productRepository.save(product);
        } catch (JsonPatchException | JsonProcessingException e) {
            throw new org.springframework.web.server.ResponseStatusException(
                    org.springframework.http.HttpStatus.BAD_REQUEST, "Invalid JSON Patch: " + e.getMessage(), e);
        }
    }

    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOS =  products.stream()
                .map(productMapper::toDto)
                .toList();
        return productDTOS;
    }

    @Transactional
    public void deleteProductById(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product with id " + id + " not found"));
        productRepository.deleteById(id);
    }
}
