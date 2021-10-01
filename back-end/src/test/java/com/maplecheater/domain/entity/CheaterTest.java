package com.maplecheater.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CheaterTest {

    @Test
    @DisplayName("생성 테스트")
    void create() {
        Cheater cheater = Cheater.builder()
                .id(1L)
                .ingameNickname("CodeDeploy")
                .ingameServer(new IngameServer(1L, "스카니아"))
                .registeredAt(LocalDateTime.now())
                .build();

        assertEquals("CodeDeploy", cheater.getIngameNickname());
    }
}