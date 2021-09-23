package com.maplecheater.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import com.maplecheater.domain.dto.request.ChangeNicknameRequestData;
import com.maplecheater.domain.dto.request.ChangePasswordRequestData;
import com.maplecheater.domain.dto.request.RegisterRequestData;
import com.maplecheater.domain.dto.response.EmailCheckResponseData;
import com.maplecheater.domain.dto.response.RegisterResponseData;
import com.maplecheater.domain.entity.Role;
import com.maplecheater.domain.type.RoleType;
import com.maplecheater.exception.InvalidVerificationException;
import com.maplecheater.exception.UnauthorizedException;
import com.maplecheater.exception.UserNotFoundException;
import com.maplecheater.service.AuthenticationService;
import com.maplecheater.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    private static final String VALID_EMAIL = "test@test.com";
    private static final String NOT_VERIFIED_EMAIL = "verified@not.com";

    // 2021-10-04 에 만료되는 토큰
    private static final String VALID_TOKEN = "eyJhbGciOiJIUzI1NiJ" +
            "9.eyJ1c2VySWQiOjEsImV4cCI6MTYzMzMxNzI5NH0" +
            ".ZKq5d0v8F4n0Er1XhTuTfFQ6pzUHPnOi3D79YaCV83k";

    private static final String USER2_VALID_TOKEN = "eyJhbGciOiJIUzI1NiJ9" +
            ".eyJ1c2VySWQiOjIsImV4cCI6MTYzMzUzODM4N30" +
            ".TKnIZyhiJKo5Whgex0546RqP1Hl4-GZpgFVPXg7jAR4";

    private static final String USER3_VALID_TOKEN = "eyJhbGciOiJIUzI1NiJ9" +
            ".eyJ1c2VySWQiOjMsImV4cCI6MTYzMzUzOTA5NH0" +
            ".MhRZvj-jjyymice2pQ-62o22rzbZdlC07PGZv3uGd_I";

    private static final String ENCODED_PASSWORD = "$2a$10$zSnzZDu5Jpyqch0zez9soekcecOTmgT8MFFzG.Sd7vClwexE.syd2";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        RegisterResponseData registerResponseData = RegisterResponseData.builder()
                .email(VALID_EMAIL)
                .nickname("nickname")
                .build();

        EmailCheckResponseData emailCheckResponseData = new EmailCheckResponseData(true);

        given(userService.registerUser(any())).will(invocation -> {
            RegisterRequestData request = invocation.getArgument(0);

            if (request.getEmail().equals(NOT_VERIFIED_EMAIL)) {
                throw new InvalidVerificationException();
            }
            return registerResponseData;
        });
        given(userService.isExistEmail(any(String.class)))
                .willReturn(emailCheckResponseData);
        given(authenticationService.getRoles(1L))
                .willReturn(Arrays.asList(new Role(1L, RoleType.USER)));

        given(authenticationService.getRoles(2L))
                .willReturn(Arrays.asList(new Role(2L, RoleType.USER)));

        given(authenticationService.getRoles(3L))
                .willReturn(Arrays.asList(new Role(3L, RoleType.USER)));

        given(authenticationService.parseToken(VALID_TOKEN))
                .willReturn(1L);

        given(authenticationService.parseToken(USER2_VALID_TOKEN))
                .willReturn(2L);

        given(authenticationService.parseToken(USER3_VALID_TOKEN))
                .willReturn(3L);


        willThrow(new UserNotFoundException())
                .given(userService)
                .unregister(2L, 2L);

        willThrow(new UnauthorizedException())
                .given(userService)
                .unregister(4L, 3L);

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
    @DisplayName("회원가입 - 실패 - 중복된 이메일")
    void register_fail_duplicated_email() {

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

    @Test
    @DisplayName("이메일 중복 확인")
    void isExistEmail_success() throws Exception {
        mockMvc.perform(get("/api/v1/users/exists/{email}", VALID_EMAIL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("isExist").exists());
    }

    @Test
    @DisplayName("비밀번호 변경 - 성공")
    void changePassword_success() throws Exception{
        ChangePasswordRequestData request = new ChangePasswordRequestData("password", "newPassword");
        mockMvc.perform(patch("/api/v1/users/{id}/password", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + VALID_TOKEN))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("닉네임 변경 - 성공")
    void changeNickname_success() throws Exception{
        ChangeNicknameRequestData request = new ChangeNicknameRequestData("newNickname");
        mockMvc.perform(patch("/api/v1/users/{id}/nickname", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + VALID_TOKEN))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("회원 탈퇴")
    void unregister() throws Exception {
        mockMvc.perform(delete("/api/v1/users/{id}", 100L)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + VALID_TOKEN))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("회원 탈퇴 - 실패 - 존재하지 않는 사용자")
    void unregister_fail_not_exists_user() throws Exception {
        mockMvc.perform(delete("/api/v1/users/{id}", 2L)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + USER2_VALID_TOKEN))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("회원 탈퇴 - 실패 - 인증 정보 불일치")
    void unregister_fail_invalid_authentication() throws Exception {
        mockMvc.perform(delete("/api/v1/users/{id}", 4L)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + USER3_VALID_TOKEN))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}