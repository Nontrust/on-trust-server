package com.ontrustserver.domain.account.exception.sub;

import com.ontrustserver.domain.account.exception.AccountDomainException;
import org.springframework.http.HttpStatus;

public class AccountNotFound extends AccountDomainException {

    @Override
    public HttpStatus statusCode() {
        return HttpStatus.NOT_FOUND;
    }
    private static final String MESSAGE = "일치하는 사용자 정보가 없습니다.";
    public AccountNotFound() {
        super(MESSAGE);
    }
}
