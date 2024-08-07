package com.stock.exchange.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

    private final int code;
    private final String error;

    NotFoundException(int code, String error, String message) {
        super(message);
        this.code = code;
        this.error = error;
    }
}
