package com.stroganov.warehouseservice.exception;

public class jwtTokenException extends Exception {

    public jwtTokenException(String message) {
        super(message);
    }

    public jwtTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
