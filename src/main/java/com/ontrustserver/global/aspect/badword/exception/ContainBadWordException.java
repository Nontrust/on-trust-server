package com.ontrustserver.global.aspect.badword.exception;

import com.ontrustserver.global.exception.AspectGlobalException;
import org.springframework.http.HttpStatus;

public class ContainBadWordException extends AspectGlobalException {
    private static final String MESSAGE_FORMAT_WITH_VALUE = "%s은 부적절한 단어입니다.\n컬럼: %s";
    @Override
    public HttpStatus statusCode() {
        return HttpStatus.UNSUPPORTED_MEDIA_TYPE;
    }
    public ContainBadWordException(String word, String param) {
        // 부적절한 단어가 포함되어있습니다. : 부적절한 문자
        super(String.format(MESSAGE_FORMAT_WITH_VALUE, word, param));
    }

    public static void throwException(String result, String name) {
        throw new ContainBadWordException(result, name);
    }
}
