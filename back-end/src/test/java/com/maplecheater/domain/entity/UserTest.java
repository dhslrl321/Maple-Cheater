package com.maplecheater.domain.entity;

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
}