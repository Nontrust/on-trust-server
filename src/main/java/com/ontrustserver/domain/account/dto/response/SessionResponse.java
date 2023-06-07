package com.ontrustserver.domain.account.dto.response;

import lombok.Builder;

public record SessionResponse(String accessToken) {
    @Builder
    public SessionResponse {
    }
}
