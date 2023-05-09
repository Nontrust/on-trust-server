package com.ontrustserver.global.filter.badword;

import org.springframework.util.AntPathMatcher;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.stream.Collectors;

public enum KorBadWord implements BadWordInterface {
    // TODO: DB저장 예정
    개새끼, 꺼져, 미친년, 미친놈, 병신, 썅, 씨발, 좆;

    private final AntPathMatcher matcher = new AntPathMatcher();
    @Override
    public String containAbuseSentence(String text) {
        String textUpperCase = text.toUpperCase(Locale.ROOT);
        return setAbuseSentenceMap().stream()
                .filter((sentence)->matcher.match(sentence, textUpperCase))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String containAbuseSentenceParallel(String blob) {
        String textUpperCase = blob.toUpperCase(Locale.ROOT);
        return setAbuseSentenceMap().parallelStream()
                .filter(word -> matcher.match(word, textUpperCase))
                .findFirst()
                .orElse(null);
    }


    @Override
    public HashSet<String> setAbuseSentenceMap() {
        return Arrays.stream(KorBadWord.values())
                .map(Enum::name)
                .collect(Collectors.toCollection(HashSet::new));
    }
}
