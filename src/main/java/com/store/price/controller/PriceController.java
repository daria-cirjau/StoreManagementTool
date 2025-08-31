package com.store.price.controller;

import com.store.price.entity.dto.PriceHistoryDTO;
import com.store.price.service.PriceService;
import com.store.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/price")
public class PriceController {
    PriceService priceService;

    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @PatchMapping(path = "/product/{id}")
    public ResponseEntity<ApiResponse> updatePrice(@PathVariable UUID id, @RequestBody PriceHistoryDTO priceHistoryDTO) {
        priceService.updatePrice(id, priceHistoryDTO);
        return ResponseEntity.ok(ApiResponse.ofMessage("Product price updated successfully", 200));
    }

    @GetMapping(path = "/product/{id}/history")
    public ResponseEntity<ApiResponse> getPriceHistory(@PathVariable UUID id) {
        List<PriceHistoryDTO> history = priceService.getPriceHistory(id);
        return ResponseEntity.ok(ApiResponse.ofData(history, 200));
    }
}
