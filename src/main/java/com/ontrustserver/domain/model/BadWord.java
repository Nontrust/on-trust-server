package com.ontrustserver.domain.model;

import com.ontrustserver.domain.badword.dto.BadWordRequest;
import com.ontrustserver.domain.model.enumerate.Language;
import jakarta.persistence.*;
import lombok.*;

@ToString
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class BadWord {
    @Id
    private String sentence;
    
    //Todo: 복합키 설정 예정 -> 이후 조회 쿼리에 실행계획에 따라 순서 변경 예정
    @Enumerated(EnumType.STRING)
    private Language language;
    private int count = 0;

    // Todo: Character.UnicodeBlock 으로 해당 sentence -> language 일치하는 단어인지 체크
    public BadWord(BadWordRequest badWordRequest) {
        this.sentence = badWordRequest.sentence();
        this.language = badWordRequest.language();
    }

    @PostLoad
    void updateCount(){
        this.count++;
    }
}
