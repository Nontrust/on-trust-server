package com.ontrustserver.global.aspect.badword.exception;

public class ContainBadWordException extends RuntimeException {
    private static final String MESSAGE_FORMAT = "%s은 부적절한 단어입니다.";
    private static final String MESSAGE_FORMAT_WITH_VALUE = "%s은 부적절한 단어입니다.\n컬럼: %s";

    public ContainBadWordException(String word) {
        // 부적절한 단어가 포함되어있습니다. : 부적절한 문자
        super( String.format(MESSAGE_FORMAT, word) );
    }
    public ContainBadWordException(String word, String param) {
        // 부적절한 단어가 포함되어있습니다. : 부적절한 문자
        super(String.format(MESSAGE_FORMAT_WITH_VALUE, word, param));
    }
}
