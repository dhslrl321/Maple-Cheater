package com.maplecheater.service;

import com.maplecheater.domain.dto.response.EvidenceImageResponseData;
import com.maplecheater.domain.entity.Report;
import com.maplecheater.domain.repository.evidence.EvidenceRepository;
import com.maplecheater.domain.repository.report.ReportRepository;
import com.maplecheater.exception.IllegalDataException;
import com.maplecheater.util.S3Uploader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@Transactional
class EvidenceServiceTest {

    private static final Long EXIST_REPORT_ID = 1L;
    private static final Long NOT_EXIST_REPORT_ID = 3L;

    private final EvidenceRepository evidenceRepository = mock(EvidenceRepository.class);
    private final ReportRepository reportRepository = mock(ReportRepository.class);
    private final S3Uploader s3Uploader = mock(S3Uploader.class);

    private EvidenceService evidenceService;

    @BeforeEach
    void setUp() {

        evidenceService = new EvidenceService(evidenceRepository, reportRepository, s3Uploader);

        given(reportRepository.findById(EXIST_REPORT_ID))
                .willReturn(Optional.of(Report.builder().id(EXIST_REPORT_ID).build()));

        given(reportRepository.findById(NOT_EXIST_REPORT_ID))
                .willReturn(Optional.empty());

        given(evidenceRepository.findAllByReportId(EXIST_REPORT_ID))
                .willReturn(List.of(new EvidenceImageResponseData(), new EvidenceImageResponseData()));

        given(evidenceRepository.findAllByReportId(NOT_EXIST_REPORT_ID))
                .willReturn(null);
    }

    @Test
    @DisplayName("report id 로 이미지 조회")
    void getImageByReportId_success() {
        List<EvidenceImageResponseData> response = evidenceService.getImageUrl(EXIST_REPORT_ID);
        assertEquals(2, response.size());
    }

    @Test
    @DisplayName("report id 로 이미지 조회 - 실패 - 존재하지 않는 report id")
    void getImageByReportId_fail() {
        IllegalDataException exception = assertThrows(IllegalDataException.class,
                () -> evidenceService.getImageUrl(NOT_EXIST_REPORT_ID));

        assertNotNull(exception.getMessage());
    }
}