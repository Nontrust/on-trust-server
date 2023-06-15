package com.ontrustserver.domain.model.enumerate;

import lombok.Getter;

import java.util.Arrays;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Getter
public enum Language {
    KOREAN,
    ENGLISH,
    JAPANESE,
    CHINESE

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
