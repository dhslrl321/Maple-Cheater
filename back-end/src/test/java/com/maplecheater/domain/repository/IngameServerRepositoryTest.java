package com.maplecheater.domain.repository;

import com.maplecheater.domain.entity.IngameServer;
import com.maplecheater.domain.repository.ingameserver.IngameServerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
class IngameServerRepositoryTest {

    @Autowired
    IngameServerRepository ingameServerRepository;

    @BeforeEach
    void setUp() {
        IngameServer ingameServer = new IngameServer("크로아");
        IngameServer saved = ingameServerRepository.save(ingameServer);

        assertEquals("크로아", saved.getServer());
    }

}