package com.stock.exchange.service;

import com.stock.exchange.entity.StockEntity;
import com.stock.exchange.entity.StockExchangeEntity;
import com.stock.exchange.exception.StockExchangeContainStockException;
import com.stock.exchange.exception.StockExchangeNotContainStockException;
import com.stock.exchange.exception.StockExchangeNotFoundException;
import com.stock.exchange.mapper.StockExchangeMapper;
import com.stock.exchange.model.StockExchangeModel;
import com.stock.exchange.repository.StockExchangeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockExchangeService {

    private final StockService stockService;
    private final StockExchangeMapper stockExchangeMapper;
    private final StockExchangeRepository stockExchangeRepository;

    public StockExchangeModel createStockExchange(StockExchangeModel stockExchangeModel) {
        final var stockExchangeEntity = stockExchangeMapper.mapModelToEntity(stockExchangeModel);
        stockExchangeRepository.save(stockExchangeEntity);
        return stockExchangeMapper.mapEntityToModel(stockExchangeEntity);
    }

    public StockExchangeModel getStockExchange(String name) {
        final var stockExchangeEntity = getOrThrowStockExchangeEntityByName(name);
        return stockExchangeMapper.mapEntityToModel(stockExchangeEntity);
    }

    public StockExchangeModel addStockToExchange(String name, String stockName) {
        final var stockExchangeEntity = getOrThrowStockExchangeEntityByName(name);
        final var stock = stockService.getStock(stockName);
        if (containsStock(stockExchangeEntity, stock)) {
            throw new StockExchangeContainStockException(String.format("Stock Exchange Already contains stock with Name: %s", stockName));
        }
        stockExchangeEntity.addStock(stock);
        updateLiveInMarketStatusAndSaveStockExchange(stockExchangeEntity);
        return stockExchangeMapper.mapEntityToModel(stockExchangeEntity);
    }

    public StockExchangeModel removeStockFromExchange(String name, String stockName) {
        final var stockExchangeEntity = getOrThrowStockExchangeEntityByName(name);
        final var stock = stockService.getStock(stockName);
        if (!containsStock(stockExchangeEntity, stock)) {
            throw new StockExchangeNotContainStockException(String.format("Stock Exchange Not contains stock to remove. Name: %s", stockName));
        }
        stockExchangeEntity.removeStock(stock);
        updateLiveInMarketStatusAndSaveStockExchange(stockExchangeEntity);
        return stockExchangeMapper.mapEntityToModel(stockExchangeEntity);
    }

    private StockExchangeEntity getOrThrowStockExchangeEntityByName(String name) {
        return stockExchangeRepository.findByName(name)
                .orElseThrow(() -> new StockExchangeNotFoundException(
                        String.format("Stock Exchange Not Found Name: %s", name)
                ));
    }

    private boolean containsStock(StockExchangeEntity stockExchangeEntity, StockEntity stock) {
        return stockExchangeEntity.getStocks().contains(stock);
    }

    private void updateLiveInMarketStatusAndSaveStockExchange(StockExchangeEntity stockExchangeEntity) {
        stockExchangeEntity.updateLiveInMarketStatus();
        stockExchangeRepository.save(stockExchangeEntity);
    }
}
