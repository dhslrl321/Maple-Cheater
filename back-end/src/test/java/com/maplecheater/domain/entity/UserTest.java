package com.maplecheater.domain.entity;

import com.maplecheater.exception.AuthenticationFailedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private static final String PASSWORD = "password";
    private static final String NEW_PASSWORD = "newPassword";
    private static final String ENCODED_PASSWORD = "$2a$10$zSnzZDu5Jpyqch0zez9soekcecOTmgT8MFFzG.Sd7vClwexE.syd2";

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Test
    @DisplayName("Entity 생성 테스트")
    void create() {
        User user = User.builder()
                .id(1L)
                .email("test@test.com")
                .password("test123")
                .registeredAt(LocalDateTime.now())
                .nickname("nickname")
                .build();

        assertNotNull(user);
    }

    @Test
    @DisplayName("비밀번호 암호화")
    void hashing() {
        User user = User.builder()
                .id(1L)
                .email("test@test.com")
                .password(ENCODED_PASSWORD)
                .registeredAt(LocalDateTime.now())
                .nickname("nickname")
                .build();

        user.changePassword(PASSWORD, NEW_PASSWORD, passwordEncoder);

        assertNotEquals(PASSWORD, user.getPassword());
    }

    @Test
    @DisplayName("비밀번호 검증")
    void authenticate() {
        User user = User.builder()
                .id(1L)
                .email("test@test.com")
                .password(ENCODED_PASSWORD)
                .registeredAt(LocalDateTime.now())
                .nickname("nickname")
                .build();

        boolean auth = user.authenticate(PASSWORD, passwordEncoder);

        assertTrue(auth);
    }

    @Test
    @DisplayName("비밀번호 변경")
    void changePassword_success() {
        User user = User.builder()
                .id(1L)
                .email("test@test.com")
                .password(ENCODED_PASSWORD)
                .registeredAt(LocalDateTime.now())
                .nickname("nickname")
                .build();

        user.changePassword(PASSWORD, "newPass", passwordEncoder);

        assertNotEquals(PASSWORD, user.getPassword());
    }

    @Test
    @DisplayName("비밀번호 변경 - 인증 실패")
    void changePassword_fail() {
        User user = User.builder()
                .id(1L)
                .email("test@test.com")
                .password(ENCODED_PASSWORD)
                .registeredAt(LocalDateTime.now())
                .nickname("nickname")
                .build();

        AuthenticationFailedException exception = assertThrows(AuthenticationFailedException.class,
                () -> user.changePassword("differentPassword", "newPassword", passwordEncoder));

        assertNotNull(exception.getMessage());
    }

    @Test
    @DisplayName("닉네임 변경")
    void changeNickname() {
        String nickname = "nickname";
        User user = User.builder()
                .id(1L)
                .email("test@test.com")
                .password(ENCODED_PASSWORD)
                .registeredAt(LocalDateTime.now())
                .nickname(nickname)
                .build();

        user.changeNickname("newNickname");

        assertNotEquals(nickname, user.getNickname());
    }

    @Test
    @DisplayName("회원 탈퇴")
    void unregister() {
        User user = User.builder()
                .id(1L)
                .email("test@test.com")
                .password("password")
                .nickname("nickname")
                .registeredAt(LocalDateTime.now())
                .build();

        assertNull(user.getUnregisteredAt());

        user.unregister();

        assertNotNull(user.getUnregisteredAt());
    }
}