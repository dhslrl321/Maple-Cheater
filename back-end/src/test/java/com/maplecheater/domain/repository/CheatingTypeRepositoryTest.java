package com.maplecheater.domain.repository;

import com.maplecheater.domain.entity.CheatingType;
import com.maplecheater.domain.repository.cheatingtype.CheatingTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
class CheatingTypeRepositoryTest {

    @Autowired
    CheatingTypeRepository cheatingTypeRepository;

    @BeforeEach
    void setUp() {
        CheatingType cash = new CheatingType("현금 거래");
        CheatingType saved = cheatingTypeRepository.save(cash);

        assertEquals(cash.getId(), saved.getId());
    }
}