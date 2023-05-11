package com.ontrustserver.global.filter.badword;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
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
        return Arrays.stream(BadWord.values())
                .map(Enum::name)
                .map(String::toLowerCase)
                .collect(Collectors.toCollection(TreeSet::new));
    }

    // TODO: DB저장 예정
    enum BadWord{
        ASSHOLE, BITCH, BLOODY, BOLLOCKS, COCK, CUNT, DAMN, DICK, FUCK, PUSSY, SHIT, SLUT
    }
}
