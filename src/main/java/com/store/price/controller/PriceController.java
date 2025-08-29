package com.store.price.controller;

import com.store.price.entity.dto.PriceHistoryDTO;
import com.store.price.service.PriceService;
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
    public ResponseEntity<String> updatePrice(@PathVariable UUID id, @RequestBody PriceHistoryDTO priceHistoryDTO) {
        priceService.updatePrice(id, priceHistoryDTO);
        return ResponseEntity.ok("Product price updated successfully");
    }

    @GetMapping(path = "/product/{id}/history")
    public ResponseEntity<List<PriceHistoryDTO>> getPriceHistory(@PathVariable UUID id) {
        return ResponseEntity.ok(priceService.getPriceHistory(id));
    }

}
