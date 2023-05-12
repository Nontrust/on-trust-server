package com.ontrustserver.global.aspect.global;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.Duration;
import java.time.Instant;

@Slf4j
@Aspect
public class RunningTimeAspect {

    /**
     * @param joinPoint: JoinPoint Object
     * 어노테이션 : @RestController
     * API호출 시간 및 정보 로그 저장 목적
     * 로그 수준 : info
     */
    @Around("@within(org.springframework.web.bind.annotation.RestController)")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        Instant start = Instant.now();
        Object result = joinPoint.proceed();
        Instant end = Instant.now();

        // proceed 전 후 시간 비교
        long durationMillis = Duration.between(start, end).toMillis();

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String requestURI = request.getRequestURI();
        String requestMethod = request.getMethod();
        int httpStatusCode = getHttpStatus(request);

        log.info("[API Call] URI : @{}({}) Status: {}, 호출 시간 {}ms", requestMethod, requestURI, httpStatusCode, durationMillis);
        return result;
    }

    private int getHttpStatus(HttpServletRequest request) {
        String statusCodeLibraryPath = "javax.servlet.error.status_code";
        Integer statusCode = (Integer) request.getAttribute(statusCodeLibraryPath);
        return statusCode != null ? statusCode : HttpStatus.INTERNAL_SERVER_ERROR.value();
    }
}
