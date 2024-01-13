package com.stroganov.exception;

public class MicroserviceCommunicationException extends Exception {
    public MicroserviceCommunicationException() {
        super();
    }

    public MicroserviceCommunicationException(String message) {
        super(message);
    }

    public MicroserviceCommunicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
