package com.ontrustserver.request;

import lombok.Builder;

public record PagingRequest(int page, int size, String order) {
    @Builder
    public PagingRequest{}
}
