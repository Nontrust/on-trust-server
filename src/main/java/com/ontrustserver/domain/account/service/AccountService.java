package com.ontrustserver.domain.account.service;

import com.ontrustserver.domain.account.dao.AccountRepository;
import com.ontrustserver.domain.account.dto.request.LoginRequest;
import com.ontrustserver.domain.account.exception.sub.AccountNotFound;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountService {
    private final AccountRepository accountRepository;
    public void login(LoginRequest loginRequest) {
        accountRepository.findByEmailAndPassword(loginRequest.email(), loginRequest.password())
                .orElseThrow(AccountNotFound::new);
    }
}
