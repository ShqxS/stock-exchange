package com.stock.exchange.service;

import com.stock.exchange.entity.StockEntity;
import com.stock.exchange.entity.StockExchangeEntity;
import com.stock.exchange.exception.StockExchangeContainStockException;
import com.stock.exchange.exception.StockExchangeNotContainStockException;
import com.stock.exchange.exception.StockExchangeNotFoundException;
import com.stock.exchange.exception.StockNotFoundException;
import com.stock.exchange.mapper.StockExchangeMapper;
import com.stock.exchange.mapper.StockMapper;
import com.stock.exchange.mapper.StockMapperImpl;
import com.stock.exchange.model.StockExchangeModel;
import com.stock.exchange.model.StockModel;
import com.stock.exchange.repository.StockExchangeRepository;
import com.stock.exchange.repository.StockRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Execution(ExecutionMode.CONCURRENT)
class StockExchangeServiceTest {
    @Mock
    StockService stockService;

    @Mock
    StockExchangeMapper stockExchangeMapper;

    @Mock
    StockExchangeRepository stockExchangeRepository;

    @InjectMocks
    StockExchangeService stockExchangeService;

    @Test
    public void createStockExchange_shouldReturnCreatedStockExchange() {

        // given
        var stockExchangeModel = new StockExchangeModel();
        var stockExchangeEntity = new StockExchangeEntity();

        when(stockExchangeMapper.mapModelToEntity(stockExchangeModel)).thenReturn(stockExchangeEntity);
        when(stockExchangeRepository.save(stockExchangeEntity)).thenReturn(stockExchangeEntity);
        when(stockExchangeMapper.mapEntityToModel(stockExchangeEntity)).thenReturn(stockExchangeModel);

        // when
        var result = stockExchangeService.createStockExchange(stockExchangeModel);

        // then
        assertNotNull(result);
        verify(stockExchangeRepository, times(1)).save(stockExchangeEntity);
        verify(stockExchangeMapper, times(1)).mapModelToEntity(stockExchangeModel);
        verify(stockExchangeMapper, times(1)).mapEntityToModel(stockExchangeEntity);
    }

    @Test
    public void getStockExchangeByName_shouldReturnStockExchangeModel() {

        // given
        var name = "NYSE";
        var stockExchangeEntity = new StockExchangeEntity();
        var stockExchangeModel = new StockExchangeModel();

        when(stockExchangeRepository.findByName(name)).thenReturn(Optional.of(stockExchangeEntity));
        when(stockExchangeMapper.mapEntityToModel(stockExchangeEntity)).thenReturn(stockExchangeModel);

        // when
        var result = stockExchangeService.getStockExchange(name);

        // then
        assertNotNull(result);
        verify(stockExchangeRepository, times(1)).findByName(name);
        verify(stockExchangeMapper, times(1)).mapEntityToModel(stockExchangeEntity);
    }

    @Test
    public void getStockExchangeByName_shouldThrowStockExchangeNotFoundException() {

        // given
        var name = "NYSE";
        when(stockExchangeRepository.findByName(name)).thenReturn(Optional.empty());

        // when
        var exception = assertThrows(StockExchangeNotFoundException.class, () -> {
            stockExchangeService.getStockExchange(name);
        });

        // then
        assertEquals("Stock Exchange Not Found Name: NYSE", exception.getMessage());
        verify(stockExchangeRepository, times(1)).findByName(name);
    }

    @Test
    public void addStockToExchange_shouldAddStockToExchange() {
        // given
        var exchangeName = "NYSE";
        var stockName = "AAPL";
        var stockExchangeEntity = new StockExchangeEntity();
        var stockEntity = new StockEntity();
        var stockExchangeModel = new StockExchangeModel();

        when(stockExchangeRepository.findByName(exchangeName)).thenReturn(Optional.of(stockExchangeEntity));
        when(stockService.getStock(stockName)).thenReturn(stockEntity);
        when(stockExchangeMapper.mapEntityToModel(stockExchangeEntity)).thenReturn(stockExchangeModel);

        // when
        var result = stockExchangeService.addStockToExchange(exchangeName, stockName);

        // then
        assertNotNull(result);
        verify(stockExchangeRepository, times(1)).findByName(exchangeName);
        verify(stockService, times(1)).getStock(stockName);
        verify(stockExchangeRepository, times(1)).save(stockExchangeEntity);
        verify(stockExchangeMapper, times(1)).mapEntityToModel(stockExchangeEntity);
    }

    @Test
    public void addStockToExchange_shouldThrowStockExchangeContainStockException() {
        // given
        var exchangeName = "NYSE";
        var stockName = "AAPL";
        var stockExchangeEntity = new StockExchangeEntity();
        var stockEntity = new StockEntity();

        stockExchangeEntity.addStock(stockEntity);

        when(stockExchangeRepository.findByName(exchangeName)).thenReturn(Optional.of(stockExchangeEntity));
        when(stockService.getStock(stockName)).thenReturn(stockEntity);

        // when
        var exception = assertThrows(StockExchangeContainStockException.class, () -> {
            stockExchangeService.addStockToExchange(exchangeName, stockName);
        });

        // then
        assertEquals("Stock Exchange Already contains stock with Name: AAPL", exception.getMessage());
        verify(stockExchangeRepository, times(1)).findByName(exchangeName);
        verify(stockService, times(1)).getStock(stockName);
        verify(stockExchangeRepository, never()).save(any()); // Ensure save was not called

    }

    @Test
    public void removeStockFromExchange_shouldRemoveStockFromExchange() {

        // given
        var exchangeName = "NYSE";
        var stockName = "AAPL";
        var stockExchangeEntity = new StockExchangeEntity();
        var stockEntity = new StockEntity();
        var stockExchangeModel = new StockExchangeModel();

        when(stockExchangeRepository.findByName(exchangeName)).thenReturn(Optional.of(stockExchangeEntity));
        when(stockService.getStock(stockName)).thenReturn(stockEntity);
        when(stockExchangeMapper.mapEntityToModel(stockExchangeEntity)).thenReturn(stockExchangeModel);

        stockExchangeEntity.addStock(stockEntity); // Ensure stock is added

        // when
        var result = stockExchangeService.removeStockFromExchange(exchangeName, stockName);

        // then
        assertNotNull(result);
        verify(stockExchangeRepository, times(1)).findByName(exchangeName);
        verify(stockService, times(1)).getStock(stockName);
        verify(stockExchangeRepository, times(1)).save(stockExchangeEntity);
        verify(stockExchangeMapper, times(1)).mapEntityToModel(stockExchangeEntity);
    }

    @Test
    public void removeStockFromExchange_shouldThrowStockExchangeNotContainStockException() {

        // given
        var exchangeName = "NYSE";
        var stockName = "AAPL";
        var stockExchangeEntity = new StockExchangeEntity();
        var stockEntity = new StockEntity();

        when(stockExchangeRepository.findByName(exchangeName)).thenReturn(Optional.of(stockExchangeEntity));
        when(stockService.getStock(stockName)).thenReturn(stockEntity);

        // when
        var exception = assertThrows(StockExchangeNotContainStockException.class, () -> {
            stockExchangeService.removeStockFromExchange(exchangeName, stockName);
        });

        // then
        assertEquals("Stock Exchange Not contains stock to remove. Name: AAPL", exception.getMessage());
        verify(stockExchangeRepository, times(1)).findByName(exchangeName);
        verify(stockService, times(1)).getStock(stockName);
        verify(stockExchangeRepository, never()).save(any());
    }
}
