package com.ontrustserver.global.aspect.badword;

import com.ontrustserver.global.aspect.badword.domain.EngBadWord;
import com.ontrustserver.global.aspect.badword.domain.KorBadWord;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class BadWordConfig {
    private final KorBadWord kor;
    private final EngBadWord eng;

    @Bean
    public BadWordCheckAspect badWordCheckAspect(){
        return new BadWordCheckAspect(kor, eng);
    }
}
