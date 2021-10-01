package com.maplecheater.domain.repository;

import com.maplecheater.domain.dto.response.ReportDetailResponseData;
import com.maplecheater.domain.dto.response.ReportPreviewResponseData;
import com.maplecheater.domain.entity.CheatingType;
import com.maplecheater.domain.entity.IngameServer;
import com.maplecheater.domain.entity.Report;
import com.maplecheater.domain.entity.User;
import com.maplecheater.domain.repository.cheatingtype.CheatingTypeRepository;
import com.maplecheater.domain.repository.ingameserver.IngameServerRepository;
import com.maplecheater.domain.repository.report.ReportRepository;
import com.maplecheater.domain.repository.user.UserRepository;
import com.maplecheater.domain.type.ReportStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
class ReportRepositoryTest {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IngameServerRepository ingameServerRepository;

    @Autowired
    private CheatingTypeRepository cheatingTypeRepository;

    @BeforeEach
    void setUp() {
        IngameServer savedIngameServer = ingameServerRepository.save(new IngameServer("크로아"));
        CheatingType savedCheatingType = cheatingTypeRepository.save(new CheatingType("현금 거래"));
        User savedUser = userRepository.save(User.builder().email("test@test.com").nickname("hello").build());
        User savedUser2 = userRepository.save(User.builder().email("test1@test.com").nickname("hel2lo").build());

        Report report = Report.builder()
                .ingameNickname("CodeDeploy")
                .cheatingDatetime(LocalDateTime.now())
                .situation("문제 상황")
                .status(ReportStatus.ACCEPTED)
                .ingameServer(savedIngameServer)
                .cheatingType(savedCheatingType)
                .user(savedUser)
                .build();

        Report report2 = Report.builder()
                .ingameNickname("CodeDeploy")
                .cheatingDatetime(LocalDateTime.now())
                .situation("문제 상황")
                .status(ReportStatus.ACCEPTED)
                .ingameServer(savedIngameServer)
                .cheatingType(savedCheatingType)
                .user(savedUser)
                .build();

        Report report3 = Report.builder()
                .ingameNickname("CodeDeploy")
                .cheatingDatetime(LocalDateTime.now())
                .situation("문제 상황")
                .status(ReportStatus.ACCEPTED)
                .ingameServer(savedIngameServer)
                .cheatingType(savedCheatingType)
                .user(savedUser2)
                .build();

        reportRepository.save(report);
        reportRepository.save(report2);
        reportRepository.save(report3);
    }

    @Test
    @DisplayName("데이터베이스에 존재하는 모든 report 를 조회한다.")
    void findAllDTO() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<ReportPreviewResponseData> reports = reportRepository.findAllDTO(pageRequest);

        assertEquals(1, reports.getTotalPages());
        assertEquals(3, reports.getTotalElements());
    }

    @Test
    @DisplayName("특정 user 의 id 로 저장한 모든 report 찾기")
    void findAllByUserIdDTO() {
        PageRequest pageRequest = PageRequest.of(0, 10);

        reportRepository.findAllByUserIdDTO(pageRequest,1L);
    }

    @Test
    @DisplayName("특정 user 가 소유하고 있는 신고서 id 를 받아 조회")
    void findByReportIdAndUserIdDTO() {
        Long userId = 1L;
        Long reportId = 1L;

        Optional<ReportDetailResponseData> result = reportRepository.findByReportIdAndUserIdDTO(reportId, userId);
        assertEquals("CodeDeploy", result.get().getCheaterNickname());
    }

}