package com.strtoganov.itemservice.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<Object> handleAccessDeniedException(
            Exception ex, WebRequest request) {
        return new ResponseEntity<>(
                "Access denied, please authorize", new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({RepositoryTransactionException.class})
    public ResponseEntity<Object> handleRepositoryTransactionException(
            Exception ex, WebRequest request) {
        return new ResponseEntity<>(
                "Transaction failed: " + ex.getMessage(), new HttpHeaders(), HttpStatus.FORBIDDEN);
    }
}
