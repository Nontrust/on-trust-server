package com.ontrustserver.domain.model.enumerate;

import lombok.Getter;

import java.util.Arrays;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Getter
public enum Language {
    // Todo : 다국어 설정 시 Key로 언어명
    //  Value로 Character.UnicodeBlock 중 단어에 해당하는 값 추가
    KOREAN(Character.UnicodeBlock.HANGUL_SYLLABLES),
    ENGLISH(Character.UnicodeBlock.BASIC_LATIN);

    private final Character.UnicodeBlock unicodeBlock;

    Language(Character.UnicodeBlock unicodeBlock) {
        this.unicodeBlock = unicodeBlock;
    }
    public static TreeSet<String> getAllLanguageEnumNames() {
        return Arrays.stream(Language.values())
                .map(Enum::name)
                .collect(Collectors.toCollection(TreeSet::new));
    }
}
