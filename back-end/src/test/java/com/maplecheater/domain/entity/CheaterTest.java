package com.maplecheater.domain.entity;

import com.maplecheater.domain.type.IngameServerType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.transaction.Transactional;

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