package com.stroganov.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({UserNotFoundException.class, UsernameNotFoundException.class})
    protected ResponseEntity<Object> handleNotFoundException(
            Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(),
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

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
