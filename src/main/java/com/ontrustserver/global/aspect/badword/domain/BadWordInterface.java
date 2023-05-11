package com.ontrustserver.global.aspect.badword.domain;

import java.util.Optional;
import java.util.TreeSet;

// TODO: 현재 AntPathMatcher 사용, O(n+m) 알고리즘 직접 구현 해보고싶음
public interface BadWordInterface {

    /**
     * 문자열이 짧은 경우 해당 단어에 욕설이 포함되어있는지 검사
     * @param text : 짧은 문자열
     * @return 욕설 포함 결과
     */
    Optional<String> containAbuseSentence(String text);

    /**
     * 긴 본문의 경우 병렬로 해당 단어에 욕설이 포함되어있는지 검사
     * @param blob : 짧은 문자열
     * @return 욕설 포함 결과
     */
    Optional<String> containAbuseSentenceParallel(String blob);
    TreeSet<String> getBadWordMap();
}
