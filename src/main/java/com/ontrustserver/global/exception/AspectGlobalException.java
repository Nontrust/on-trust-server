package com.ontrustserver.global.exception;

import org.springframework.http.HttpStatus;

public abstract class AspectGlobalException extends RuntimeException {
    public abstract HttpStatus statusCode();
    public AspectGlobalException(String message) {
        super(message);
    }
}
