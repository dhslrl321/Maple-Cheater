package com.maplecheater.domain.repository;

import com.maplecheater.domain.entity.Cheater;
import com.maplecheater.domain.entity.IngameServer;
import com.maplecheater.domain.repository.cheater.CheaterRepository;
import com.maplecheater.domain.repository.ingameserver.IngameServerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.transaction.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
class CheaterRepositoryTest {

    @Autowired
    private CheaterRepository cheaterRepository;

    @Autowired
    private IngameServerRepository ingameServerRepository;

    @BeforeEach
    void setUp() {
        IngameServer ingameServer = ingameServerRepository.save(new IngameServer("크로아"));

        Cheater cheater = Cheater.builder()
                .ingameNickname("CodeDeploy")
                .registeredAt(LocalDateTime.now())
                .ingameServer(ingameServer)
                .build();

        cheaterRepository.save(cheater);
    }

    @Test
    @DisplayName("테스트")
    void t() {

    }

}