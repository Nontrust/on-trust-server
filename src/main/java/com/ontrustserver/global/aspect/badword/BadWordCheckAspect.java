package com.ontrustserver.global.aspect.badword;

import com.ontrustserver.global.aspect.badword.domain.BadWord;
import com.ontrustserver.global.aspect.badword.domain.EngBadWord;
import com.ontrustserver.global.aspect.badword.domain.KorBadWord;
import com.ontrustserver.global.aspect.badword.exception.ContainBadWordException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.lang.reflect.Field;
import java.util.Optional;

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
     * @return proceed
     *
     * 어노테이션 : @BadWord
     * 메서드 파라미터의 Record에서 Filed Type이 String인 컨텐츠를 검사합니다.
     * 욕설이 포함되어있을 시 ContainBadWordException을 발생시킵니다.
     */
    @Around("@annotation(badWord)")
    public Object checkBadWordPointCut(ProceedingJoinPoint joinPoint, BadWord badWord) throws Throwable {

        Object[] args = joinPoint.getArgs();

        for (Object arg : args) {
            if (arg instanceof Record) {
                Field[] fields = arg.getClass().getDeclaredFields();
                for (Field field :fields){
                    field.setAccessible(true);
                    Object value = field.get(arg);
                    String name = field.getName();
                    if (value instanceof String) {
                        // String 타입의 필드인 경우 욕설 필터링을 적용합니다.
                        Optional<String> checkResult = kor.containAbuseSentence((String) value);
                        if(checkResult.isPresent()) {
                            throw new ContainBadWordException(checkResult.get(), name);
                        }
                    }
                    field.setAccessible(false);
                }
            }
        }
        return joinPoint.proceed();
    }
}
