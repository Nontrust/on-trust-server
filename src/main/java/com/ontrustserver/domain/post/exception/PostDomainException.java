package com.ontrustserver.domain.post.exception;

import org.springframework.http.HttpStatus;

public abstract class PostDomainException extends RuntimeException {
    public abstract HttpStatus statusCode();
    public PostDomainException(String message) {
        super(message);
    }
}
