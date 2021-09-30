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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
class CheaterRepositoryTest {

    private static final String INGAME_NICKNAME = "cheaters";

    @Autowired
    private CheaterRepository cheaterRepository;

    @Autowired
    private IngameServerRepository ingameServerRepository;

    @BeforeEach
    void setUp() {
        IngameServer ingameServer = ingameServerRepository.save(new IngameServer("크로아"));

        Cheater cheater = Cheater.builder()
                .ingameNickname(INGAME_NICKNAME)
                .registeredAt(LocalDateTime.now())
                .ingameServer(ingameServer)
                .build();

        cheaterRepository.save(cheater);
    }

    @Test
    @DisplayName("cheater 이름으로 cheating 정보 모두 조회")
    void findByIngameNickname() {
        Optional<Cheater> optionalCheater = cheaterRepository.findByIngameNickname(INGAME_NICKNAME);
        assertNotNull(optionalCheater.get());
    }

}