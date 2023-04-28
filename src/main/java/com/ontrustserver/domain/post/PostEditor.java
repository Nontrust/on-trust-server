package com.ontrustserver.domain.post;

import lombok.Builder;

public record PostEditor(
        String title,
        String contents
) {
    @Builder
    public PostEditor {}
}
