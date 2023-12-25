package com.stroganov.exception;

public class ServiceValidationException extends Exception {
    public ServiceValidationException() {
    }

    public ServiceValidationException(String message) {
        super(message);
    }
}
