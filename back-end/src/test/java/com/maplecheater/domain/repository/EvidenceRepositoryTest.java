package com.maplecheater.domain.repository;

import com.maplecheater.domain.dto.response.EvidenceImageResponseData;
import com.maplecheater.domain.entity.Evidence;
import com.maplecheater.domain.entity.Report;
import com.maplecheater.domain.repository.evidence.EvidenceRepository;
import com.maplecheater.domain.repository.report.ReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
class EvidenceRepositoryTest {

    @Autowired
    private EvidenceRepository evidenceRepository;

    @Autowired
    private ReportRepository reportRepository;

    @BeforeEach
    void setUp() {
        Report report = reportRepository.save(new Report());

        evidenceRepository.save(Evidence.builder()
                .imageUrl("url")
                .report(report)
                .build());

        evidenceRepository.save(Evidence.builder()
                .imageUrl("url2")
                .report(report)
                .build());
    }

    @Test
    @DisplayName("report id 에 따른 모든 image 조회")
    void findAllByReportId() {
        List<EvidenceImageResponseData> result = evidenceRepository.findAllByReportId(1L);
        assertEquals(2, result.size());
    }
}