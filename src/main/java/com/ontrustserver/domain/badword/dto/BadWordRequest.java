package com.ontrustserver.domain.badword.dto;

import com.ontrustserver.domain.model.enumerate.Language;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

public record BadWordRequest(
        @NotEmpty
        @Size(max = 10, min = 1, message = "1~10글자 이내의 단어만 입력이 가능합니다.")
        @Pattern(regexp = "\\S+", message = "공백을 사용할 수 없습니다.")
        String sentence,
        @NotNull @Enumerated(EnumType.STRING)
        Language language
) {
    @Builder
    public BadWordRequest {
    }
}
