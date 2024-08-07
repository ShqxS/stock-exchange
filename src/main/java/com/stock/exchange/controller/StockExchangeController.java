package com.stock.exchange.controller;

import com.stock.exchange.controller.request.StockAddRequest;
import com.stock.exchange.controller.request.StockExchangeRequest;
import com.stock.exchange.controller.request.StockRemoveRequest;
import com.stock.exchange.controller.response.StockExchangeResponse;
import com.stock.exchange.mapper.StockExchangeMapper;
import com.stock.exchange.service.StockExchangeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/stock-exchange")
@RequiredArgsConstructor
public class StockExchangeController {

    private final StockExchangeMapper stockExchangeMapper;
    private final StockExchangeService stockExchangeService;


    @PostMapping
    public ResponseEntity<StockExchangeResponse> createStockExchange(@RequestBody StockExchangeRequest stockExchangeRequest) {
        final var stockExchangeModel = stockExchangeService.createStockExchange(stockExchangeMapper.mapRequestToModel(stockExchangeRequest));
        return ResponseEntity.ok(stockExchangeMapper.mapModelToResponse(stockExchangeModel));
    }

    @GetMapping("/{name}")
    public ResponseEntity<StockExchangeResponse> getStockExchange(@PathVariable String name) {
        final var stockExchangeModel = stockExchangeService.getStockExchange(name);
        return ResponseEntity.ok(stockExchangeMapper.mapModelToResponse(stockExchangeModel));
    }

    @PostMapping("/{name}")
    public ResponseEntity<StockExchangeResponse> addStockToExchange(@PathVariable String name, @RequestBody StockAddRequest stockAddRequest) {
        final var stockExchangeModel = stockExchangeService.addStockToExchange(name, stockAddRequest.getStockName());
        return ResponseEntity.ok(stockExchangeMapper.mapModelToResponse(stockExchangeModel));
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<StockExchangeResponse> removeStockFromExchange(@PathVariable String name, @RequestBody StockRemoveRequest stockRemoveRequest) {
        final var stockExchangeModel = stockExchangeService.removeStockFromExchange(name, stockRemoveRequest.getStockName());
        return ResponseEntity.ok(stockExchangeMapper.mapModelToResponse(stockExchangeModel));
    }

}
