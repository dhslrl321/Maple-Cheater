package com.maplecheater.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

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
}