package com.stock.exchange.exception;

import com.stock.exchange.util.Constants;
import lombok.Getter;

@Getter
public class StockExchangeContainStockException extends ExpectedException {

    public StockExchangeContainStockException(String message) {
        super(Constants.Error.STOCK_EXCHANGE_CONTAIN_STOCK, StockExchangeContainStockException.class.getName(), message);
    }
}
