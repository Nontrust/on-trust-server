package com.ontrustserver.global.exception.sup;

import com.ontrustserver.global.exception.AuthorizedGlobalException;
import org.springframework.http.HttpStatus;

public class Unauthorized extends AuthorizedGlobalException {
    @Override
    public HttpStatus statusCode() {
        return HttpStatus.UNAUTHORIZED;
    }
    public Unauthorized() {
        super();
    }
    public Unauthorized(String message) {
        super(message);
    }
}
