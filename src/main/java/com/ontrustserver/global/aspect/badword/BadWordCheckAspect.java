package com.ontrustserver.global.aspect.badword;

import com.ontrustserver.global.aspect.badword.domain.EngBadWord;
import com.ontrustserver.global.aspect.badword.domain.KorBadWord;
import com.ontrustserver.global.aspect.badword.exception.ContainBadWordException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import java.lang.reflect.Field;

@Slf4j
@Aspect
public class BadWordCheckAspect {
    private final KorBadWord kor;
    private final EngBadWord eng;

    public BadWordCheckAspect(KorBadWord kor, EngBadWord eng) {
        this.kor = kor;
        this.eng = eng;
    }

    /**
     * @param joinPoint: JoinPoint Object
     * @param badWord: target Annotation Interface
     * 어노테이션 : @BadWord
     * 메서드 파라미터의 Record에서 Filed Type이 String인 컨텐츠를 검사합니다.
     * 욕설이 포함되어있을 시 ContainBadWordException을 발생시킵니다.
     */
    @Before("@annotation(badWord)")
    public void checkBadWordPointCut(JoinPoint joinPoint, BadWord badWord) throws Throwable {
        Object[] args = joinPoint.getArgs();
        for (var arg : args) {
            if (arg instanceof Record record) {
                Field[] fields = record.getClass().getDeclaredFields();
                for (Field field :fields){
                    field.setAccessible(true);
                    var value = field.get(record);
                    String name = field.getName();
                    if (value instanceof String fieldName) {
                        // String 타입의 필드인 경우 욕설 필터링을 적용합니다.
                        kor.containAbuseSentence(fieldName)
                                .ifPresent((result)
                                    -> ContainBadWordException.throwException(result, name));
                    }
                    field.setAccessible(false);
                }
            }
        }
    }
}
