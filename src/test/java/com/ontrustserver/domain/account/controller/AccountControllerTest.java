package com.ontrustserver.domain.account.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ontrustserver.domain.account.dao.AccountRepository;
import com.ontrustserver.domain.account.dto.request.LoginRequest;
import com.ontrustserver.domain.model.Account;
import com.ontrustserver.global.auth.domain.dao.SessionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private SessionRepository sessionRepository;


    @BeforeEach
    void setPost(){
        // given
        Account account = Account.builder()
                .name("테스트")
                .email("deed1515@naver.com")
                .password("1234")
                .build();
        accountRepository.save(account);
    }

    @AfterEach
    void cleanRepository() {
        accountRepository.deleteAll();
        sessionRepository.deleteAll();
    }

    @Test
    @DisplayName("로그인 실패")
    void loginFailedTest() throws Exception {
        LoginRequest request = LoginRequest.builder()
                .email("not")
                .password("1234")
                .build();
        String json = objectMapper.writeValueAsString(request);

        mockMvc
                .perform(post("/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.validation.parameter", containsString("정보가 없")))
                .andDo(print())
                .andReturn();
    }

    @Test
    @DisplayName("로그인 성공 이후 세션 생성 테스트")
    void sessionTest() throws Exception {
        //given
        LoginRequest request = LoginRequest.builder()
                .email("deed1515@naver.com")
                .password("1234")
                .build();
        String json = objectMapper.writeValueAsString(request);

        Instant expectedTime = Instant.now();
        Instant lowerInstant = expectedTime.minus(1, ChronoUnit.SECONDS);
        Instant upperInstant = expectedTime.plus(1, ChronoUnit.SECONDS);

        //expect
        mockMvc
                .perform(post("/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        //then
        Account account = accountRepository.findByEmailAndPassword("deed1515@naver.com", "1234")
                .orElseThrow();
        assertNotNull(account.getSessions());
        assertEquals(1L, sessionRepository.count());
    }
    
    @Test
    @DisplayName("로그인 성공")
    void loginTest() throws Exception {
        //given
        LoginRequest request = LoginRequest.builder()
                .email("deed1515@naver.com")
                .password("1234")
                .build();
        String json = objectMapper.writeValueAsString(request);

        Instant expectedTime = Instant.now();
        Instant lowerInstant = expectedTime.minus(1, ChronoUnit.SECONDS);
        Instant upperInstant = expectedTime.plus(1, ChronoUnit.SECONDS);

        //expect
        MvcResult mvcResult = mockMvc
                .perform(post("/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

}