package com.stock.exchange.service;

import com.stock.exchange.entity.StockEntity;
import com.stock.exchange.exception.StockNotFoundException;
import com.stock.exchange.mapper.StockMapper;
import com.stock.exchange.model.StockModel;
import com.stock.exchange.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockService {

    private final StockMapper stockMapper;
    private final StockRepository stockRepository;

    public StockModel createStock(StockModel stockModel) {
        final var stockEntity = stockMapper.mapModelToEntity(stockModel);
        stockRepository.save(stockEntity);
        return stockMapper.mapEntityToModel(stockEntity);
    }

    @Transactional
    public void deleteStock(String name) {
        stockRepository.deleteByName(name);
    }

    public StockModel updateStockPrice(String name, BigDecimal newPrice) {
        final var stockEntity = stockRepository.findByName(name)
                .orElseThrow(() -> new StockNotFoundException(String.format("Stock Not Found. Name: %s", name)));

        stockEntity.setCurrentPrice(newPrice);
        stockRepository.save(stockEntity);
        return stockMapper.mapEntityToModel(stockEntity);

    }

    public StockEntity getStock(String name) {
        return stockRepository.findByName(name)
                .orElseThrow(() -> new StockNotFoundException(String.format("Stock Not Found. Name: %s", name)));
    }
}
