package com.ontrustserver.global.exception;

import org.springframework.http.HttpStatus;

public abstract class AuthorizedGlobalException extends RuntimeException {
    public abstract HttpStatus statusCode();
    public AuthorizedGlobalException() {
        super();
    }
    public AuthorizedGlobalException(String message) {
        super(message);
    }
}
