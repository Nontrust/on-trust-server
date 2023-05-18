package com.ontrustserver.global.aspect.badword;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ontrustserver.domain.model.Post;
import com.ontrustserver.domain.post.dao.PostRepository;
import com.ontrustserver.domain.post.dto.request.PostRequest;
import com.ontrustserver.global.aspect.badword.constance.TestSentence;
import com.ontrustserver.global.aspect.badword.domain.BadWordInterface;
import com.ontrustserver.global.aspect.badword.domain.EngBadWord;
import com.ontrustserver.global.aspect.badword.domain.KorBadWord;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StopWatch;

import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@AutoConfigureMockMvc
@SpringBootTest
public class BadWordFilterTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setPost(){
        // given
        Post post = Post.builder()
                        .title("글")
                        .contents("컨텐츠")
                        .build();
        postRepository.save(post);
    }
    @AfterEach
    void cleanRepository() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("영어 필터링 메서드 테스트")
    void EngBadWordTest(){
        // given
        String badSentence = TestSentence.LOREM_IPSUM_CONTAIN_DAMN;
        String goodSentence = TestSentence.LOREM_IPSUM;
        BadWordInterface eng = new EngBadWord();

        // when
        Optional<String> containAbuse = eng.containAbuseSentence(badSentence);
        Optional<String> nonContainAbuse = eng.containAbuseSentence(goodSentence);

        Optional<String> containAbuseParallel = eng.containAbuseSentenceParallel(badSentence);
        Optional<String> nonContainAbuseParallel = eng.containAbuseSentenceParallel(goodSentence);

        //then

        assertTrue(containAbuse.isPresent());
        assertFalse(nonContainAbuse.isPresent());

        assertTrue(containAbuseParallel.isPresent());
        assertFalse(nonContainAbuseParallel.isPresent());
    }

    @Test
    @DisplayName("국문 필터링 메서드 테스트")
    void KorBadWordTest(){
        // given
        String badSentence = TestSentence.HUN_MIN_JEONG_EUM_CONTAIN_BAD_SENTENCE;
        String goodSentence = TestSentence.HUN_MIN_JEONG_EUM;
        BadWordInterface kor = new KorBadWord();

        // when
        Optional<String> containAbuse = kor.containAbuseSentence(badSentence);
        Optional<String> nonContainAbuse = kor.containAbuseSentence(goodSentence);

        Optional<String> containAbuseParallel = kor.containAbuseSentenceParallel(badSentence);
        Optional<String> nonContainAbuseParallel = kor.containAbuseSentenceParallel(goodSentence);

        //then
        assertTrue(containAbuse.isPresent());
        assertEquals(containAbuse.get(), KorBadWord.BadWordEnum.JERK.getSentence());
        assertFalse(nonContainAbuse.isPresent());

        assertTrue(containAbuseParallel.isPresent());
        assertEquals(containAbuseParallel.get(), KorBadWord.BadWordEnum.JERK.getSentence());
        assertFalse(nonContainAbuseParallel.isPresent());
    }
    @Test
    @DisplayName("병렬 처리 시간 검사 : 반드시 통과하지 않을 수도 있음")
    void checkKorParallelTime(){
        // given
        KorBadWord kor = new KorBadWord();
        StopWatch stopWatch = new StopWatch();

        String blob = TestSentence.HUN_MIN_JEONG_EUM.repeat(10000);

        String blobWithBadWord = TestSentence.HUN_MIN_JEONG_EUM.repeat(1000)
                +KorBadWord.BadWordEnum.FUCK.getSentence()
                +KorBadWord.BadWordEnum.RETARD.getSentence()
                +TestSentence.HUN_MIN_JEONG_EUM.repeat(9000);

        String blobWithBadWordLast = TestSentence.HUN_MIN_JEONG_EUM.repeat(10000)
                +KorBadWord.BadWordEnum.FUCK.getSentence();

        // when
        // 일반적인 큰 단어 검사 시
        // Non Parallel
        stopWatch.start();
        kor.containAbuseSentence(blob);
        stopWatch.stop();
        long blobTime = stopWatch.getLastTaskTimeNanos();
        // parallel
        stopWatch.start();
        kor.containAbuseSentenceParallel(blob);
        stopWatch.stop();
        long blobTimeParallel = stopWatch.getLastTaskTimeNanos();

        // 욕설이 중간에 포함된 단어 검사 시
        // Non Parallel
        stopWatch.start();
        kor.containAbuseSentence(blobWithBadWord);
        stopWatch.stop();
        long blobBadWordTime = stopWatch.getLastTaskTimeNanos();

        // Parallel
        stopWatch.start();
        kor.containAbuseSentenceParallel(blobWithBadWord);
        stopWatch.stop();
        long blobBadWordTimeParallel = stopWatch.getLastTaskTimeNanos();

        // 욕설이 마지막에 포함된 단어 검사 시
        // Non Parallel
        stopWatch.start();
        kor.containAbuseSentenceParallel(blobWithBadWordLast);
        stopWatch.stop();
        long blobTimeWorst = stopWatch.getLastTaskTimeNanos();

        // Parallel
        stopWatch.start();
        kor.containAbuseSentenceParallel(blobWithBadWordLast);
        stopWatch.stop();
        long blobTimeWorstParallel = stopWatch.getLastTaskTimeNanos();
        // then
        /*
        assertTrue(blobTime >= blobTimeParallel);
        assertTrue(blobBadWordTime >= blobBadWordTimeParallel);
        assertTrue(blobTimeWorst >= blobTimeWorstParallel);
         */
    }

    @Test
    void testBadWordInPostTitle() throws Exception {
        String badTitle = TestSentence.HUN_MIN_JEONG_EUM_CONTAIN_BAD_SENTENCE;
        String goodContents = TestSentence.HUN_MIN_JEONG_EUM;

        PostRequest postRequest = PostRequest.builder().title(badTitle).contents(goodContents).build();
        int status = HttpStatus.UNSUPPORTED_MEDIA_TYPE.value();

        String json = objectMapper.writeValueAsString(postRequest);
        long beforeCount = postRepository.count();

        // expect
        mockMvc
                .perform(
                        post("/post")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isUnsupportedMediaType())
                .andExpect(jsonPath("$.code", is(status)))
                .andExpect(jsonPath("$.message", containsString("잘못된")))
                .andExpect(jsonPath("$.validation.parameter", containsString("부적절한 단어입니다")))
                .andDo(print())
                .andReturn();

        // then
        long afterCount = postRepository.count();
        assertFalse(afterCount > beforeCount);
    }

    @Test
    void testBadWordInPostUpdateTitle() throws Exception {
        String badTitle = TestSentence.HUN_MIN_JEONG_EUM_CONTAIN_BAD_SENTENCE;

        PostRequest postRequest = PostRequest.builder().title(badTitle).build();
        int status = HttpStatus.UNSUPPORTED_MEDIA_TYPE.value();

        String json = objectMapper.writeValueAsString(postRequest);
        long beforeCount = postRepository.count();
        long id = postRepository.fetchAnyOne().getId();

        // expect
        mockMvc
                .perform(
                        put("/post/{postId}", id)
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isUnsupportedMediaType())
                .andExpect(jsonPath("$.code", is(status)))
                .andExpect(jsonPath("$.message", containsString("잘못된")))
                .andExpect(jsonPath("$.validation.parameter", containsString("부적절한 단어입니다")))
                .andDo(print())
                .andReturn();

        // then
        long afterCount = postRepository.count();
        assertFalse(afterCount > beforeCount);
    }
}
