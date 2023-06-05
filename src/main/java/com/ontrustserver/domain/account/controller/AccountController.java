package com.ontrustserver.domain.account.controller;

import com.ontrustserver.domain.account.dto.request.LoginRequest;
import com.ontrustserver.domain.account.service.AccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RequestMapping("/auth")
@RestController
public class AccountController {
    private final AccountService authService;

    @PostMapping("/login")
    public void login(@RequestBody LoginRequest loginRequest){
        authService.signin(loginRequest);
    }
}
