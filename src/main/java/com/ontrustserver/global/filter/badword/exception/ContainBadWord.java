package com.ontrustserver.global.filter.badword.exception;

public class ContainBadWord extends RuntimeException {
    private static final String MESSAGE_FORMAT = "%s에 부적절한 단어가 포함되어 있습니다 : %s";
    public ContainBadWord(String param, String word) {
        // 부적절한 단어가 포함되어있습니다. : 부적절한 문자
        super( String.format(MESSAGE_FORMAT, param, word) );
    }
}
