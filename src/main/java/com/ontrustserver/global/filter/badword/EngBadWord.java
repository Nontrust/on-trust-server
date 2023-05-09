package com.ontrustserver.global.filter.badword;

import org.springframework.util.AntPathMatcher;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

public enum EngBadWord implements BadWordInterface {
    // TODO: DB저장 예정
    ASSHOLE, BITCH, BLOODY, BOLLOCKS, COCK, CUNT, DAMN, DICK, FUCK, PUSSY, SHIT, SLUT;

    private final AntPathMatcher matcher = new AntPathMatcher();

    @Override
    public String containAbuseSentence(String text) {
        return setAbuseSentenceMap().stream()
                .filter((sentence)->matcher.match(sentence, text))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String containAbuseSentenceParallel(String blob) {
        // TODO: 현재 AntPathMatcher 사용, O(n+m) 알고리즘 직접 구현 해보고싶음
        return setAbuseSentenceMap().parallelStream()
                .filter(word -> matcher.match(word, blob))
                .findFirst()
                .orElse(null);
    }

    @Override
    public HashSet<String> setAbuseSentenceMap() {
        return Arrays.stream(KorBadWord.values())
                .map(Enum::name)
                .map(String::toUpperCase)
                .collect(Collectors.toCollection(HashSet::new));
    }
}
