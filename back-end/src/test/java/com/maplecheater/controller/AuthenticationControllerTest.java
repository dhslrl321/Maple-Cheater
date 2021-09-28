package com.maplecheater.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import com.maplecheater.domain.dto.request.LoginRequestData;
import com.maplecheater.domain.dto.response.LoginResponseData;
import com.maplecheater.domain.dto.response.ValidateUserResponseData;
import com.maplecheater.domain.entity.Role;
import com.maplecheater.domain.type.RoleType;
import com.maplecheater.exception.*;
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

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthenticationController.class)
class AuthenticationControllerTest {

    // 2021-10-04 에 만료되는 토큰
    private static final String VALID_TOKEN = "eyJhbGciOiJIUzI1NiJ" +
            "9.eyJ1c2VySWQiOjEsImV4cCI6MTYzMzMxNzI5NH0" +
            ".ZKq5d0v8F4n0Er1XhTuTfFQ6pzUHPnOi3D79YaCV83k";

    private static final String INVALID_TOKEN = "eyJhbGciOiJIUzI1NiJ9" +
            ".eyJ1c2VySWQiOjIsImV4cCI6MTYzMzUzODM4N30" +
            ".TKnIZyhiJKo5Whgex0546RqP1Hl4-GZpgFVPXg7jAR4";

    private static final String EMAIL = "test@test.com";
    private static final String NOT_EXIST_EMAIL = "not@exist.com";
    private static final String INVALID_PASSWORD = "pass";

    private static final String NEW_USER_EMAIL = "new@new.com";
    private static final String INVALID_CODE = "432123";
    private static final String VERIFIED_AND_SERVICE_USER_EMAIL = "service@service.com";
    private static final String USER_WITH_NOT_EXIST_VERIFICATION = "not@exists.verification";

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

        given(authenticationService.parseToken(VALID_TOKEN))
                .willReturn(1L);

        given(authenticationService.getRoles(1L))
                .willReturn(List.of(new Role(1L, RoleType.USER)));

        given(authenticationService.validateUser(1L))
                .willReturn(ValidateUserResponseData.builder()
                        .email("test@gm.com")
                        .nickname("on")
                        .userId(1L)
                        .build());

        willThrow(new InvalidTokenException())
                .given(authenticationService)
                .parseToken(INVALID_TOKEN);

        willThrow(new VerificationNotFoundException())
                .given(mailService)
                .authenticate(USER_WITH_NOT_EXIST_VERIFICATION, INVALID_CODE);

        willThrow(new UserNotFoundException())
                .given(mailService)
                .sendAuthMail(VERIFIED_AND_SERVICE_USER_EMAIL);

        willThrow(new UserNotFoundException())
                .given(mailService)
                .sendTempPasswordMail(NOT_EXIST_EMAIL);

        willThrow(new AuthenticationFailedException())
                .given(mailService)
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
        mockMvc.perform(get("/api/v1/authenticate/email/{email}", NEW_USER_EMAIL))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("이메일 전송 - 실패 - 존재하는 회원")
    void mailAuthenticate_fail() throws Exception {
        mockMvc.perform(get("/api/v1/authenticate/email/{email}", VERIFIED_AND_SERVICE_USER_EMAIL))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("인증 코드 비교")
    void authCode_success() throws Exception {
        mockMvc.perform(get("/api/v1/authenticate/email/code/{email}/{code}", NEW_USER_EMAIL, "123456"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("인증 코드 비교 - 실패 - 인증 정보가 없음")
    void authCode_fail_not_exist_verification() throws Exception {
        mockMvc.perform(get("/api/v1/authenticate/email/code/{email}/{code}", USER_WITH_NOT_EXIST_VERIFICATION, INVALID_CODE))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("인증 코드 비교 - 실패 - 올바르지 않은 인증 코드")
    void authCode_fail_invalid_code() throws Exception {
        mockMvc.perform(get("/api/v1/authenticate/email/code/{email}/{code}", NEW_USER_EMAIL, INVALID_CODE))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("임시 비밀번호 발급")
    void tempPassword_success() throws Exception {
        mockMvc.perform(get("/api/v1/authenticate/password/{email}", EMAIL))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("임시 비밀번호 발급")
    void tempPassword_fail() throws Exception {
        mockMvc.perform(get("/api/v1/authenticate/password/{email}", NOT_EXIST_EMAIL))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("유저 유효성 검증 - 성공")
    void validateUser_success() throws Exception {
        mockMvc.perform(get("/api/v1/authenticate/validate")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + VALID_TOKEN))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("email").exists());
    }

    @Test
    @DisplayName("유저 유효성 검증 - 실패 - 존재하지 않는 사용자")
    void validateUser_fail() throws Exception {
        mockMvc.perform(get("/api/v1/authenticate/validate")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + INVALID_TOKEN))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}