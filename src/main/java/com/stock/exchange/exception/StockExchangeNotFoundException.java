package com.stock.exchange.exception;

import com.stock.exchange.util.Constants;

public class StockExchangeNotFoundException extends NotFoundException {

    public StockExchangeNotFoundException(String message) {
        super(Constants.Error.STOCK_EXCHANGE_NOT_FOUND, StockExchangeNotFoundException.class.getName(), message);
    }
}
