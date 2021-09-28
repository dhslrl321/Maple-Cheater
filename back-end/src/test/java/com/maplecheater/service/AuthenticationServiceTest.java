package com.maplecheater.service;

import com.maplecheater.domain.dto.request.LoginRequestData;
import com.maplecheater.domain.dto.response.LoginResponseData;
import com.maplecheater.domain.dto.response.ValidateUserResponseData;
import com.maplecheater.domain.entity.Role;
import com.maplecheater.domain.entity.User;
import com.maplecheater.domain.repository.role.RoleRepository;
import com.maplecheater.domain.repository.user.UserRepository;
import com.maplecheater.domain.type.RoleType;
import com.maplecheater.exception.AuthenticationFailedException;
import com.maplecheater.exception.UserDeletedException;
import com.maplecheater.exception.UserNotFoundException;
import com.maplecheater.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.mock;

class AuthenticationServiceTest {

    private static final String SECRET = "66555555444444333333222222111111";

    private static final String EMAIL = "test@test.com";
    private static final String DELETED_USER_EMAIL = "del@del.com";
    private static final String NOT_EXISTS_USER_EMAIL = "not@exists.user";
    private static final String WRONG_PASS_USER_EMAIL = "wrong@password.user";

    private static final Long USER_ID = 1004L;
    private static final Long INVALID_USER_ID = 1004L;
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
                .id(1L)
                .email(EMAIL)
                .password(ENCODED_PASSWORD)
                .nickname("nickname")
                .build();

        User deletedUser = User.builder()
                .id(1L)
                .email(DELETED_USER_EMAIL)
                .password(ENCODED_PASSWORD)
                .nickname("nickname")
                .unregisteredAt(LocalDateTime.now())
                .build();

        authenticationService = new AuthenticationService(userRepository, roleRepository, jwtUtil, passwordEncoder);

        given(userRepository.findByEmail(EMAIL))
                .willReturn(Optional.of(user));
        given(userRepository.findByEmail(DELETED_USER_EMAIL))
                .willReturn(Optional.of(deletedUser));

        given(userRepository.findByEmail(NOT_EXISTS_USER_EMAIL))
                .willReturn(Optional.empty());

        given(roleRepository.findAllByUserId(USER_ID))
                .willReturn(Collections.singletonList(new Role(USER_ID, RoleType.USER)));

        given(roleRepository.findAllByUserId(INVALID_USER_ID))
                .willReturn(Collections.singletonList(new Role(INVALID_USER_ID, RoleType.USER)));

        given(userRepository.findById(USER_ID))
                .willReturn(Optional.of(user));

        given(userRepository.findById(INVALID_USER_ID))
                .willReturn(Optional.empty());

        willThrow(new AuthenticationFailedException())
                .given(userRepository)
                .findByEmail(WRONG_PASS_USER_EMAIL);
    }

    @Test
    @DisplayName("로그인 - 성공")
    void login_success() {
        LoginRequestData request = LoginRequestData.builder()
                .email(EMAIL)
                .password("password")
                .build();

        LoginResponseData response = authenticationService.login(request);
        System.out.println(response);
        assertAll(
                () -> assertEquals(EMAIL, response.getEmail()),
                () -> assertNotNull(response.getUserId()),
                () -> assertNotNull(response.getAccessToken())
        );

    }

    @Test
    @DisplayName("로그인 - 실패 - 탈퇴된 회원")
    void login_fail_deleted_user() {
        LoginRequestData request = LoginRequestData.builder()
                .email(DELETED_USER_EMAIL)
                .password("password")
                .build();

        UserDeletedException exception = assertThrows(UserDeletedException.class,
                () -> authenticationService.login(request));

        assertNotNull(exception.getMessage());
    }

    @Test
    @DisplayName("로그인 - 실패 - 존재하지 않는 회원")
    void login_fail_not_exist_user() {
        LoginRequestData request = LoginRequestData.builder()
                .email("fail@test.com")
                .password("password")
                .build();

        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> authenticationService.login(request));

        assertNotNull(exception.getMessage());
    }

    @Test
    @DisplayName("로그인 - 실패 - 비밀번호가 다름")
    void login_fail_different_password() {
        LoginRequestData request = LoginRequestData.builder()
                .email(WRONG_PASS_USER_EMAIL)
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

    @Test
    @DisplayName("parseToken - 깨진 테스트 수정해야함")
    void parseToken() {
        Long parsedUserId = authenticationService.parseToken(VALID_TOKEN);
        // parsedUserId 에서 널포남
    }

    @Test
    @DisplayName("validateUser_success")
    void validateUser_success() {
        ValidateUserResponseData response = authenticationService.validateUser(USER_ID);

        assertNotNull(response);
    }

    @Test
    @DisplayName("validateUser_fail")
    void validateUser_fail() {
        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> authenticationService.validateUser(INVALID_USER_ID));

        assertNotNull(exception.getMessage());
    }
}