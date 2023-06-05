package com.ontrustserver.domain.account.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

public record LoginRequest(
        @NotBlank(message = "이메일을 입력해 주세요") String email,
        @NotBlank(message = "비밀번호를 입력해 주세요") String password
) {
    @Builder
    public LoginRequest {
    }
}
