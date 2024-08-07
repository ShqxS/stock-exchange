package com.stock.exchange.exception;

import com.stock.exchange.util.Constants;

public class StockNotFoundException extends NotFoundException {

    public StockNotFoundException(String message) {
        super(Constants.Error.STOCK_NOT_FOUND, StockNotFoundException.class.getName(), message);
    }
}
