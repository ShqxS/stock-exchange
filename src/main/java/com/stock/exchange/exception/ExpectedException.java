package com.stock.exchange.exception;

import lombok.Getter;

@Getter
public class ExpectedException extends RuntimeException {

    private final int code;
    private final String error;

    ExpectedException(int code, String error, String message) {
        super(message);
        this.code = code;
        this.error = error;
    }
}
