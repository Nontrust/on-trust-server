package com.ontrustserver.request;

import jakarta.validation.constraints.Min;
import lombok.Builder;


public record PagingRequest(
        @Min(value = 1L, message = "page는 0보다 작을 수 없습니다") Integer page,
        @Min(value = 1L, message = "size는 0보다 작을 수 없습니다") Integer size,
        String order
) {
    @Builder
    public PagingRequest(Integer page, Integer size, String order) {
        this.page = page==null ? 1 : page;
        this.size = size==null ? 10 : size;
        this.order = order==null ? "asc" : order;
    }
}
