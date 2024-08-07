package com.stock.exchange.service;

import com.stock.exchange.entity.StockEntity;
import com.stock.exchange.exception.StockNotFoundException;
import com.stock.exchange.mapper.StockMapper;
import com.stock.exchange.mapper.StockMapperImpl;
import com.stock.exchange.model.StockModel;
import com.stock.exchange.repository.StockRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Execution(ExecutionMode.CONCURRENT)
class StockServiceTest {

    @Mock
    StockMapper stockMapper;

    @Mock
    StockRepository stockRepository;

    @InjectMocks
    StockService stockService;

    @Test
    void createStock_shouldReturnCreatedStock() {
        // given
        var stockModel = StockModel.builder()
                .name("SISE")
                .description("Sisecam")
                .currentPrice(BigDecimal.valueOf(150.00))
                .build();

        var stockEntity = new StockEntity();
        stockEntity.setName("SISE");
        stockEntity.setCurrentPrice(BigDecimal.valueOf(150.00));
        stockEntity.setDescription("Sisecam");

        when(stockMapper.mapModelToEntity(stockModel)).thenReturn(stockEntity);
        when(stockRepository.save(stockEntity)).thenReturn(stockEntity);
        when(stockMapper.mapEntityToModel(stockEntity)).thenReturn(stockModel);

        // when
        var createdStock = stockService.createStock(stockModel);

        // then
        assertNotNull(createdStock);
        assertEquals(stockModel, createdStock);
        verify(stockRepository, times(1)).save(stockEntity);
    }


    @Test
    void deleteStock_shouldDeleteStockByName() {
        // given
        var stockName = "TestStock";

        doNothing().when(stockRepository).deleteByName(stockName);

        // when
        stockService.deleteStock(stockName);

        // then
        verify(stockRepository, times(1)).deleteByName(stockName);
    }

    @Test
    void updateStockPrice_shouldUpdateAndReturnUpdatedStock() {
        // given
        var stockName = "TestStock";
        var newPrice = BigDecimal.valueOf(150);
        var stockEntity = new StockEntity(); // Initialize with appropriate data
        stockEntity.setName(stockName);
        stockEntity.setCurrentPrice(BigDecimal.valueOf(100));

        when(stockRepository.findByName(stockName)).thenReturn(Optional.of(stockEntity));
        when(stockRepository.save(stockEntity)).thenReturn(stockEntity);
        when(stockMapper.mapEntityToModel(stockEntity)).thenReturn(new StockModel()); // Initialize with appropriate data

        // Act
        var updatedStock = stockService.updateStockPrice(stockName, newPrice);

        // Assert
        assertNotNull(updatedStock);
        assertEquals(newPrice, stockEntity.getCurrentPrice());
        verify(stockRepository, times(1)).save(stockEntity);
    }

    @Test
    void updateStockPrice_shouldThrowStockNotFoundException_WhenStockNotFound() {
        // given
        var stockName = "NonExistingStock";
        var newPrice = BigDecimal.valueOf(150);

        when(stockRepository.findByName(stockName)).thenReturn(Optional.empty());

        // when & then
        assertThrows(StockNotFoundException.class, () -> stockService.updateStockPrice(stockName, newPrice));
        verify(stockRepository, never()).save(any(StockEntity.class));
    }

    @Test
    void getStock_shouldReturnStockEntity_WhenStockExists() {
        // given
        var stockName = "TestStock";
        var stockEntity = new StockEntity(); // Initialize with appropriate data
        stockEntity.setName(stockName);

        when(stockRepository.findByName(stockName)).thenReturn(Optional.of(stockEntity));

        // when
        var foundStock = stockService.getStock(stockName);

        // then
        assertNotNull(foundStock);
        assertEquals(stockEntity, foundStock);
    }

    @Test
    void getStock_shouldThrowStockNotFoundException_WhenStockNotFound() {
        // given
        var stockName = "NonExistingStock";

        when(stockRepository.findByName(stockName)).thenReturn(Optional.empty());

        // when & then
        assertThrows(StockNotFoundException.class, () -> stockService.getStock(stockName));

    }

}
