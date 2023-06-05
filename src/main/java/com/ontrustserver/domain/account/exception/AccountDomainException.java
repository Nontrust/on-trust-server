package com.ontrustserver.domain.account.exception;

import org.springframework.http.HttpStatus;

public abstract class AccountDomainException extends RuntimeException {
    public abstract HttpStatus statusCode();
    public AccountDomainException(String message) {
        super(message);
    }
}
