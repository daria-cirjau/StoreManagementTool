package com.store.product.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.store.category.entity.Category;
import com.store.category.repository.CategoryRepository;
import com.store.product.entity.Product;
import com.store.product.entity.dto.ProductDTO;
import com.store.product.entity.mapper.ProductMapper;
import com.store.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createProduct_shouldSaveAndReturnDto() {
        Category category = Category.builder().id(UUID.randomUUID()).name("Electronics").build();
        Product product = new Product();
        product.setCategory(category);

        ProductDTO dto = ProductDTO.builder().id(UUID.randomUUID()).name("Phone").build();

        when(productMapper.toEntity(dto)).thenReturn(product);
        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toDto(product)).thenReturn(dto);

        ProductDTO result = productService.createProduct(dto);

        assertEquals(dto, result);
        verify(productRepository).save(product);
    }

    @Test
    void getProduct_shouldReturnProductDto() {
        UUID id = UUID.randomUUID();
        Product product = new Product();
        ProductDTO dto = ProductDTO.builder().id(id).name("Test").build();

        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        when(productMapper.toDto(product)).thenReturn(dto);

        ProductDTO result = productService.getProduct(id);

        assertEquals(dto, result);
    }

    @Test
    void getProduct_shouldThrowIfNotFound() {
        UUID id = UUID.randomUUID();
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> productService.getProduct(id));
    }

    @Test
    void deleteProductById_shouldDeleteIfExists() {
        UUID id = UUID.randomUUID();
        Product product = new Product();

        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        productService.deleteProductById(id);

        verify(productRepository).deleteById(id);
    }

    @Test
    void deleteProductById_shouldThrowIfNotFound() {
        UUID id = UUID.randomUUID();
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> productService.deleteProductById(id));
    }

    @Test
    void getAllProducts_shouldMapAllToDto() {
        Product p1 = new Product();
        Product p2 = new Product();
        ProductDTO d1 = ProductDTO.builder().name("A").build();
        ProductDTO d2 = ProductDTO.builder().name("B").build();

        when(productRepository.findAll()).thenReturn(List.of(p1, p2));
        when(productMapper.toDto(p1)).thenReturn(d1);
        when(productMapper.toDto(p2)).thenReturn(d2);

        List<ProductDTO> result = productService.getAllProducts();

        assertEquals(2, result.size());
        assertTrue(result.contains(d1));
        assertTrue(result.contains(d2));
    }

    @Test
    void getProductsByCategoryName_shouldReturnMappedDtos() {
        Category category = Category.builder().id(UUID.randomUUID()).name("Electronics").build();
        Product product = new Product();
        ProductDTO dto = ProductDTO.builder().name("Phone").build();

        when(categoryRepository.findDistinctFirstByName("Electronics")).thenReturn(Optional.of(category));
        when(productRepository.findByCategory(category)).thenReturn(List.of(product));
        when(productMapper.toDto(product)).thenReturn(dto);

        List<ProductDTO> result = productService.getProductsByCategoryName("Electronics");

        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
    }

    @Test
    void getProductsByCategoryId_shouldThrowIfCategoryNotFound() {
        UUID catId = UUID.randomUUID();
        when(categoryRepository.findById(catId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> productService.getProductsByCategoryId(catId));
    }

    @Test
    void patchProduct_shouldThrowBadRequestOnInvalidPatch() throws Exception {
        UUID id = UUID.randomUUID();
        Product product = new Product();
        ProductDTO dto = ProductDTO.builder().id(id).name("Phone").build();
        JsonPatch patch = mock(JsonPatch.class);

        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        when(productMapper.toDto(product)).thenReturn(dto);
        when(objectMapper.valueToTree(dto)).thenReturn(mock(com.fasterxml.jackson.databind.JsonNode.class));
        when(patch.apply(any())).thenThrow(new com.github.fge.jsonpatch.JsonPatchException("Invalid"));

        assertThrows(ResponseStatusException.class, () -> productService.patchProduct(id, patch));
    }
}
