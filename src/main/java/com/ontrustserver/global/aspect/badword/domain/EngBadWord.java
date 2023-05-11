package com.ontrustserver.global.aspect.badword.domain;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Component
public class EngBadWord implements BadWordInterface {

    @Override
    public Optional<String> containAbuseSentence(String text) {
        String textUpperCase = text.toLowerCase(Locale.ROOT);
        return getBadWordMap().stream()
                .filter(textUpperCase::contains)
                .findFirst();
    }

    @Override
    public Optional<String> containAbuseSentenceParallel(String blob) {
        String lowerCase = blob.toLowerCase(Locale.ROOT);
        //Todo: 병렬 스트리밍 문제 시 HashTable 사용 예정
        return getBadWordMap()
                .parallelStream()
                .filter(lowerCase::contains)
                .findFirst();
    }

    @Override
    public TreeSet<String> getBadWordMap() {
        return Arrays.stream(BadWordEnum.values())
                .map(Enum::name)
                .map(String::toLowerCase)
                .collect(Collectors.toCollection(TreeSet::new));
    }

    // TODO: DB저장 예정
    public enum BadWordEnum{
        ASSHOLE, BITCH, BLOODY, BOLLOCKS, COCK, CUNT, DAMN, DICK, FUCK, PUSSY, SHIT, SLUT
    }
}
