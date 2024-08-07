package com.stock.exchange.exception;

import lombok.Data;

@Data
public class ErrorResponse {
    private Integer errorCode;
    private String message;

    public ErrorResponse(Integer errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

}
