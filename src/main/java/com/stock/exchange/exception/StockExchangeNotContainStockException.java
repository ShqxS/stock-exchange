package com.stock.exchange.exception;

import com.stock.exchange.util.Constants;
import lombok.Getter;

@Getter
public class StockExchangeNotContainStockException extends ExpectedException {

    public StockExchangeNotContainStockException(String message) {
        super(Constants.Error.STOCK_EXCHANGE_NOT_CONTAIN_STOCK, StockExchangeNotContainStockException.class.getName(), message);
    }
}
