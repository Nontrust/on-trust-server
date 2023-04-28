package com.ontrustserver.domain.model;

import lombok.Builder;

public record PostEditor(
        String title,
        String contents
) {
    @Builder
    public PostEditor {}
}
