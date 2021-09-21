package com.maplecheater.service;

import com.maplecheater.domain.dto.request.LoginRequestData;
import com.maplecheater.domain.dto.response.LoginResponseData;
import com.maplecheater.domain.entity.Role;
import com.maplecheater.domain.entity.User;
import com.maplecheater.domain.repository.role.RoleRepository;
import com.maplecheater.domain.repository.user.UserRepository;
import com.maplecheater.domain.type.RoleType;
import com.maplecheater.exception.AuthenticationFailedException;
import com.maplecheater.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class AuthenticationServiceTest {

    private static final String SECRET = "66555555444444333333222222111111";
    private static final String EMAIL = "test@test.com";
    private static final Long USER_ID = 1004L;
    private static final String ENCODED_PASSWORD = "$2a$10$zSnzZDu5Jpyqch0zez9soekcecOTmgT8MFFzG.Sd7vClwexE.syd2";
    private static final String VALID_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MzMzNjY2MDF9.grfL76CaNeaDo2N-EW25KYCg-SGkwBz1MoQ12MFhSzI";

    private RoleRepository roleRepository = mock(RoleRepository.class);
    private UserRepository userRepository = mock(UserRepository.class);

    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        JwtUtil jwtUtil = new JwtUtil(SECRET);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        User user = User.builder()
                .email(EMAIL)
                .password(ENCODED_PASSWORD)
                .nickname("nickname")
                .build();

        authenticationService = new AuthenticationService(userRepository, roleRepository, jwtUtil, passwordEncoder);

        given(userRepository.findByEmail(EMAIL)).willReturn(Optional.of(user));
        given(roleRepository.findAllByUserId(USER_ID))
                .willReturn(Collections.singletonList(new Role(USER_ID, RoleType.USER)));
    }

    @Test
    @DisplayName("로그인 - 성공")
    void login_success() {
        LoginRequestData request = LoginRequestData.builder()
                .email(EMAIL)
                .password("password")
                .build();

        LoginResponseData response = authenticationService.login(request);
        assertAll(
                () -> assertEquals(EMAIL, response.getEmail()),
                () -> assertNotNull(response.getUserId()),
                () -> assertNotNull(response.getAccessToken())
        );

    }

    @Test
    @DisplayName("로그인 - 실패 - 비밀번호가 다름")
    void login_fail_different_password() {
        LoginRequestData request = LoginRequestData.builder()
                .email("fail@test.com")
                .password("password")
                .build();

        AuthenticationFailedException exception = assertThrows(AuthenticationFailedException.class,
                () -> authenticationService.login(request));

        assertNotNull(exception.getMessage());
    }

    @Test
    @DisplayName("권한 조회")
    void getRoles_success() {
        List<Role> roles = authenticationService.getRoles(USER_ID);

        assertEquals(1, roles.size());
        assertEquals(RoleType.USER, roles.get(0).getRoleType());
    }
}