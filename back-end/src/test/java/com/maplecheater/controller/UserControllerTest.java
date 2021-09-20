package com.maplecheater.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maplecheater.domain.dto.request.RegisterRequestData;
import com.maplecheater.domain.dto.response.RegisterResponseData;
import com.maplecheater.exception.InvalidVerificationException;
import com.maplecheater.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    private static final String VALID_EMAIL = "test@test.com";
    private static final String NOT_VERIFIED_EMAIL = "verified@not.com";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @BeforeEach
    void setUp() {
        RegisterResponseData registerResponseData = RegisterResponseData.builder()
                .email(VALID_EMAIL)
                .nickname("nickname")
                .build();

        given(userService.registerUser(any())).will(invocation -> {
            RegisterRequestData request = invocation.getArgument(0);

            if (request.getEmail().equals(NOT_VERIFIED_EMAIL)) {
                throw new InvalidVerificationException();
            }
            return registerResponseData;
        });
    }

    @Test
    @DisplayName("회원가입 - 성공")
    void register() throws Exception {
        RegisterRequestData validRegisterRequest = RegisterRequestData.builder()
                .email(VALID_EMAIL)
                .password("password")
                .nickname("nickname")
                .build();

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRegisterRequest)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("email").exists())
                .andExpect(jsonPath("nickname").exists());
    }

    @Test
    @DisplayName("회원가입 - 실패 - 인증되지 않은 이메일")
    void register_fail_not_verified_email() throws Exception {
        RegisterRequestData invalidRequest = RegisterRequestData.builder()
                .email(NOT_VERIFIED_EMAIL)
                .password("password")
                .nickname("nickname")
                .build();

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").exists());
    }

}