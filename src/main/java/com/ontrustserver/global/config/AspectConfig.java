package com.ontrustserver.global.config;

import com.ontrustserver.global.aspect.badword.BadWordCheckAspect;
import com.ontrustserver.global.aspect.badword.domain.EngBadWord;
import com.ontrustserver.global.aspect.badword.domain.KorBadWord;
import com.ontrustserver.global.aspect.global.RunningTimeAspect;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AspectConfig {
    private final KorBadWord kor;
    private final EngBadWord eng;

    @Bean(name = "badWordCheckAspect")
    public BadWordCheckAspect badWordCheckAspect(){
        return new BadWordCheckAspect(kor, eng);
    }
    @Bean(name = "runningTimeAspect")
    public RunningTimeAspect runningTimeAspect(){
        return new RunningTimeAspect();
    }
}
