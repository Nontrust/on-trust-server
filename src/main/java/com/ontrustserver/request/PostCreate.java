package com.ontrustserver.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;


public record PostCreate(
        @NotEmpty(message = "title은 필수입니다.") String title,
        @NotEmpty(message = "contents는 필수입니다.")String contents
){
    @Builder
    public PostCreate(String title, String contents){
        this.title = title;
        this.contents = contents;
    }
}

