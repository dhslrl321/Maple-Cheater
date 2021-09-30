package com.maplecheater.domain.repository;

import com.maplecheater.domain.entity.Cheater;
import com.maplecheater.domain.entity.CheaterDetail;
import com.maplecheater.domain.entity.CheatingType;
import com.maplecheater.domain.entity.IngameServer;
import com.maplecheater.domain.repository.cheater.CheaterRepository;
import com.maplecheater.domain.repository.cheaterdetail.CheaterDetailRepository;
import com.maplecheater.domain.repository.cheatingtype.CheatingTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.transaction.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
class CheaterDetailRepositoryTest {

    @Autowired
    private CheaterDetailRepository cheaterDetailRepository;

    @Autowired
    private CheaterRepository cheaterRepository;

    @Autowired
    private CheatingTypeRepository cheatingTypeRepository;


    @BeforeEach
    void setUp() {
        Cheater cheater = Cheater.builder()
                .ingameNickname("CodeDeploy")
                .registeredAt(LocalDateTime.now())
                .ingameServer(new IngameServer(1L, "크로아"))
                .build();

        Cheater savedCheater = cheaterRepository.save(cheater);

        CheatingType savedCheatingType = cheatingTypeRepository.save(new CheatingType("현금 거리"));

        CheaterDetail cheaterDetail = CheaterDetail.builder()
                .cheatingDatetime(LocalDateTime.now())
                .situation("문제 상황입니다")
                .cheater(savedCheater)
                .cheatingType(savedCheatingType)
                .build();

        cheaterDetailRepository.save(cheaterDetail);
    }

}