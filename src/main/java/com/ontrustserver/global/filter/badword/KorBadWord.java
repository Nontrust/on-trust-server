package com.ontrustserver.global.filter.badword;

import java.util.Arrays;
import java.util.Optional;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class KorBadWord implements BadWordInterface {
    @Override
    public Optional<String> containAbuseSentence(String text) {
        return getBadWordMap().stream()
                .filter(text::contains)
                .findFirst();
    }

    @Override
    public Optional<String> containAbuseSentenceParallel(String blob) {
        //Todo: 병렬 스트리밍 문제 시 HashTable 사용 예정
        return getBadWordMap().parallelStream()
                .filter(blob::contains)
                .findFirst();
    }


    @Override
    public TreeSet<String> getBadWordMap() {
        return Arrays.stream(BadWord.values())
                .map(BadWord::getSentence)
                .collect(Collectors.toCollection(TreeSet::new));
    }

    // TODO: DB저장 예정
    enum BadWord {
        SON_OF_A_BITCH("개새끼"),
        GO_TO_HELL("꺼져"),
        CRAZY_BITCH("미친년"),
        CRAZY_MAN("미친놈"),
        RETARD("병신"),
        JERK("썅"),
        FUCK("씨발"),
        DICK("좆");

        private final String sentence;

        BadWord(String sentence) {
            this.sentence = sentence;
        }

        public String getSentence() {
            return sentence;
        }
    }
}
