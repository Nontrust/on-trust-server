package com.ontrustserver.domain.account.util;

import com.ontrustserver.domain.account.dto.response.SessionResponse;

public class AccountResponseUtil {
    public static SessionResponse of(String accessToken){
        return SessionResponse.builder()
                .accessToken(accessToken)
                .build();
    }
}
