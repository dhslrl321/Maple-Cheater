package com.maplecheater.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CheaterDetailTest {

    @Test
    @DisplayName("생성 테스트")
    void create() {
        String nickname = "CodeDeploy";
        String server = "크로아";
        String type = "현금 거래";

        Cheater cheater = Cheater.builder()
                .ingameNickname(nickname)
                .registeredAt(LocalDateTime.now())
                .ingameServer(new IngameServer(server))
                .build();

        CheatingType cheatingType = new CheatingType(1L, type);

        CheaterDetail detail = CheaterDetail.builder()
                .cheater(cheater)
                .situation("현금 거래에서 사기가 발생함")
                .cheatingDatetime(LocalDateTime.now())
                .cheatingType(cheatingType)
                .build();

        assertAll(
                () -> assertNotNull(detail),
                () -> assertEquals(nickname, detail.getCheater().getIngameNickname()),
                () -> assertEquals(server, detail.getCheater().getIngameServer().getServer()),
                () -> assertEquals(type, detail.getCheatingType().getType())
        );
    }

}