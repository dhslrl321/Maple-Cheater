package com.maplecheater.domain.repository;

import com.maplecheater.domain.entity.Cheater;
import com.maplecheater.domain.entity.CheaterDetail;
import com.maplecheater.domain.entity.CheatingType;
import com.maplecheater.domain.entity.IngameServer;
import com.maplecheater.domain.repository.cheater.CheaterRepository;
import com.maplecheater.domain.repository.cheaterdetail.CheaterDetailRepository;
import com.maplecheater.domain.repository.cheatingtype.CheatingTypeRepository;
import com.maplecheater.domain.repository.ingameserver.IngameServerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

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
    @Autowired
    private IngameServerRepository ingameServerRepository;

    @BeforeEach
    void setUp() {

        IngameServer server = ingameServerRepository.save(new IngameServer("크로아"));
        CheatingType savedCheatingType = cheatingTypeRepository.save(new CheatingType("현금 거래"));

        Cheater cheater = Cheater.builder()
                .ingameNickname("cheater")
                .registeredAt(LocalDateTime.now())
                .ingameServer(server)
                .build();
        Cheater cheater2 = Cheater.builder()
                .ingameNickname("cheater123")
                .registeredAt(LocalDateTime.now())
                .ingameServer(server)
                .build();

        Cheater savedCheater = cheaterRepository.save(cheater);
        Cheater savedCheater2 = cheaterRepository.save(cheater2);

        CheaterDetail cheaterDetail1 = CheaterDetail.builder()
                .cheatingDatetime(LocalDateTime.now())
                .situation("문제 상황입니다")
                .cheater(savedCheater)
                .cheatingType(savedCheatingType)
                .build();

        CheaterDetail cheaterDetail2 = CheaterDetail.builder()
                .cheatingDatetime(LocalDateTime.now())
                .situation("문제 상황입니다")
                .cheater(savedCheater)
                .cheatingType(savedCheatingType)
                .build();

        CheaterDetail cheaterDetail3 = CheaterDetail.builder()
                .cheatingDatetime(LocalDateTime.now())
                .situation("문제 상황입니다")
                .cheater(savedCheater)
                .cheatingType(savedCheatingType)
                .build();

        CheaterDetail cheaterDetail4 = CheaterDetail.builder()
                .cheatingDatetime(LocalDateTime.now())
                .situation("문제 상황입니다")
                .cheater(savedCheater2)
                .cheatingType(savedCheatingType)
                .build();

        cheaterDetailRepository.save(cheaterDetail1);
        cheaterDetailRepository.save(cheaterDetail2);
        cheaterDetailRepository.save(cheaterDetail3);
        cheaterDetailRepository.save(cheaterDetail4);
    }

    @Test
    @DisplayName("findAllByIngameNickname")
    void findAllByIngameNickname() {
        String ingameNickname = "cheater";
        List<CheaterDetail> cheaterDetails = cheaterDetailRepository.findAllByCheaterNickname(ingameNickname);

        CheaterDetail cheaterDetail = cheaterDetails.get(0);

        assertAll(
                () -> assertEquals(ingameNickname, cheaterDetail.getCheater().getIngameNickname()),
                () -> assertEquals(3, cheaterDetails.size())
        );
    }
}