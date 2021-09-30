package com.maplecheater.domain.entity;

import com.maplecheater.domain.type.ReportStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ReportTest {

    @Test
    @DisplayName("생성 테스트")
    void create() {

        Report report = Report.builder()
                .id(1L)
                .ingameNickname("CodeDeploy")
                .cheatingDatetime(LocalDateTime.now())
                .situation("상황")
                .status(ReportStatus.ACCEPTED)
                .user(new User())
                .cheatingType(new CheatingType())
                .ingameServer(new IngameServer())
                .build();

        assertAll(
                () -> assertNotNull(report.getUser()),
                () -> assertEquals(1L, report.getId()),
                () -> assertEquals("상황", report.getSituation())
        );
    }

}