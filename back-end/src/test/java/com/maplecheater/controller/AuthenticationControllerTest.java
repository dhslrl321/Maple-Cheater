package com.maplecheater.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maplecheater.domain.dto.request.LoginRequestData;
import com.maplecheater.domain.dto.response.LoginResponseData;
import com.maplecheater.exception.AuthenticationFailedException;
import com.maplecheater.exception.UserExistsException;
import com.maplecheater.service.AuthenticationService;
import com.maplecheater.service.MailService;
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
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthenticationController.class)
class AuthenticationControllerTest {

    // 2021-10-04 에 만료되는 토큰
    private static final String VALID_TOKEN = "eyJhbGciOiJIUzI1NiJ" +
            "9.eyJ1c2VySWQiOjEsImV4cCI6MTYzMzMxNzI5NH0" +
            ".ZKq5d0v8F4n0Er1XhTuTfFQ6pzUHPnOi3D79YaCV83k";

    private static final String EMAIL = "test@test.com";
    private static final String INVALID_PASSWORD = "pass";

    private static final String NEW_USER_EMAIL = "new@new.com";
    private static final String INVALID_CODE = "432123";
    private static final String VERIFIED_AND_SERVICE_USER_EMAIL = "service@service.com";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private MailService mailService;

    @BeforeEach
    void setUp() {
        given(authenticationService.login(any(LoginRequestData.class))).will(invocation -> {
            LoginRequestData argument = invocation.getArgument(0);

            String password = argument.getPassword();

            if(password.equals(INVALID_PASSWORD)) {
                throw new AuthenticationFailedException();
            }
            return LoginResponseData.builder()
                    .userId(1L)
                    .email(EMAIL)
                    .accessToken(VALID_TOKEN)
                    .build();
        });

        doThrow(UserExistsException.class).when(mailService)
                .sendMail(VERIFIED_AND_SERVICE_USER_EMAIL);

        doThrow(AuthenticationFailedException.class).when(mailService)
                .authenticate(NEW_USER_EMAIL, INVALID_CODE);
    }

    @Test
    @DisplayName("로그인 시 토큰 발급 테스트")
    void login() throws Exception {
        LoginRequestData request = LoginRequestData.builder()
                .email(EMAIL)
                .password("password")
                .build();

        mockMvc.perform(post("/api/v1/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("userId").exists())
                .andExpect(jsonPath("email").exists())
                .andExpect(jsonPath("accessToken").exists());
    }

    @Test
    @DisplayName("로그인 실패 - 비밀번호 불일치")
    void login_fail_incorrect_password() throws Exception {
        LoginRequestData request = LoginRequestData.builder()
                .email(EMAIL)
                .password(INVALID_PASSWORD)
                .build();

        mockMvc.perform(post("/api/v1/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("message").exists());
    }

    @Test
    @DisplayName("이메일 전송 - 성공")
    void mailAuthenticate_success() throws Exception {
        mockMvc.perform(get("/api/v1/authenticate/{email}", NEW_USER_EMAIL))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("이메일 전송 - 실패 - 존재하는 회원")
    void mailAuthenticate_fail() throws Exception {
        mockMvc.perform(get("/api/v1/authenticate/{email}", VERIFIED_AND_SERVICE_USER_EMAIL))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("인증 코드 비교")
    void authCode_success() throws Exception {
        mockMvc.perform(get("/api/v1/authenticate/{email}/{code}", NEW_USER_EMAIL, "123456"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("인증 코드 비교 - 실패")
    void authCode_fail() throws Exception {
        mockMvc.perform(get("/api/v1/authenticate/{email}/{code}", NEW_USER_EMAIL, INVALID_CODE))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}