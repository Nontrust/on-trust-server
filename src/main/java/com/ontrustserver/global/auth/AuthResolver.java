package com.ontrustserver.global.auth;

import com.ontrustserver.domain.model.Session;
import com.ontrustserver.global.auth.domain.AuthSession;
import com.ontrustserver.global.auth.domain.dao.SessionRepository;
import com.ontrustserver.global.exception.sup.Unauthorized;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Optional;

import static org.springframework.util.StringUtils.hasText;

public class AuthResolver implements HandlerMethodArgumentResolver{

    private final SessionRepository sessionRepository;

    public AuthResolver(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(AuthSession.class);
    }

    @Override
    public Object resolveArgument(@Nullable MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String authorization = webRequest.getHeader("Authorization");
        if (!hasText(authorization)) {
            throw new Unauthorized();
        }

        Optional<Session> byAccessToken = sessionRepository.findByAccessToken(authorization);


        return new AuthSession(1L, "MockUser");
    }
}
