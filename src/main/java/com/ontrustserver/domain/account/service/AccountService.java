package com.ontrustserver.domain.account.service;

import com.ontrustserver.domain.account.dao.AccountRepository;
import com.ontrustserver.domain.account.dto.request.LoginRequest;
import com.ontrustserver.domain.account.exception.sub.AccountNotFound;
import com.ontrustserver.domain.model.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Transactional
    public void signin(LoginRequest loginRequest) {
        Account account = accountRepository.findByEmailAndPassword(loginRequest.email(), loginRequest.password())
                .orElseThrow(AccountNotFound::new);
        account.addSession();
    }
}
