package com.stock.exchange.controller;

import com.stock.exchange.controller.request.StockRequest;
import com.stock.exchange.controller.request.StockUpdateRequest;
import com.stock.exchange.controller.response.StockResponse;
import com.stock.exchange.mapper.StockMapper;
import com.stock.exchange.service.StockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/stock")
@RequiredArgsConstructor
public class StockController {

    private final StockMapper stockMapper;
    private final StockService stockService;

    @PostMapping
    public ResponseEntity<StockResponse> createStock(@Valid @RequestBody StockRequest stockRequest) {
        final var stockModel = stockService.createStock(stockMapper.mapRequestToModel(stockRequest));
        return ResponseEntity.ok(stockMapper.mapModelToResponse(stockModel));
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteStock(@PathVariable String name) {
        stockService.deleteStock(name);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{name}")
    public ResponseEntity<StockResponse> updateStockPrice(@PathVariable String name, @RequestBody @Valid StockUpdateRequest stockUpdateRequest) {
        final var stockModel = stockService.updateStockPrice(name, stockUpdateRequest.getNewPrice());
        return ResponseEntity.ok(stockMapper.mapModelToResponse(stockModel));
    }


}
