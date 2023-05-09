package com.ontrustserver.global.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public record ErrorResponse(
        int code,
        String message,
        Map<String, String> validation
){
    @Builder
    public ErrorResponse(int code, String message, Map<String, String> validation){
        this.code=code;
        this.message=message;
        this.validation = validation == null ? new HashMap<>() : Map.copyOf(validation);
    }
}
