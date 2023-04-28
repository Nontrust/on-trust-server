package com.ontrustserver.global.error;

import lombok.Builder;

import java.util.HashMap;
import java.util.Map;

public record ErrorResponse(
        String code,
        String message,
        Map<String, String> validation
){
    @Builder
    public ErrorResponse(String code, String message, Map<String, String> validation){
        this.code=code;
        this.message=message;
        this.validation = validation == null ? new HashMap<>() : Map.copyOf(validation);
    }
}
