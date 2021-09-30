package com.maplecheater.domain.repository;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.transaction.Transactional;

import java.time.LocalDateTime;

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

        Report report = Report.builder()
                .ingameNickname("CodeDeploy")
                .cheatingDatetime(LocalDateTime.now())
                .situation("문제 상황")
                .status(ReportStatus.ACCEPTED)
                .ingameServer(savedIngameServer)
                .cheatingType(savedCheatingType)
                .user(savedUser)
                .build();

        reportRepository.save(report);
    }

}