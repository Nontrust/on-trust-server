package com.ontrustserver.domain.post.dto.response;

import lombok.Builder;

public record PostResponse(
        Long id,
        String title,
        String contents
) {
    @Builder
    public PostResponse {
    }
}
