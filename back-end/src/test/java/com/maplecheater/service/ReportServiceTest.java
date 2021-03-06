package com.maplecheater.service;

import com.maplecheater.domain.dto.request.AddReportRequestData;
import com.maplecheater.domain.dto.request.UpdateReportStatusRequestData;
import com.maplecheater.domain.dto.response.*;
import com.maplecheater.domain.entity.CheatingType;
import com.maplecheater.domain.entity.IngameServer;
import com.maplecheater.domain.entity.Report;
import com.maplecheater.domain.entity.User;
import com.maplecheater.domain.repository.cheatingtype.CheatingTypeRepository;
import com.maplecheater.domain.repository.evidence.EvidenceRepository;
import com.maplecheater.domain.repository.ingameserver.IngameServerRepository;
import com.maplecheater.domain.repository.report.ReportRepository;
import com.maplecheater.domain.repository.user.UserRepository;
import com.maplecheater.domain.type.ReportStatus;
import com.maplecheater.exception.IllegalDataException;
import com.maplecheater.exception.UnauthorizedException;
import com.maplecheater.util.S3Uploader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ReportServiceTest {

    private static final Long INVALID_USER = 22L;
    private static final Long EXIST_USER = 1L;
    private static final Long NOT_EXIST_USER = 44L;

    private static final Long EXIST_SERVER = 1L;
    private static final Long NOT_EXIST_SERVER = 44L;

    private static final Long EXIST_CHEATING_TYPE = 1L;
    private static final Long NOT_EXIST_CHEATING_TYPE = 44L;

    private static final Integer PAGE_INDEX = 0;
    private static final Integer PAGE_SIZE = 5;

    private static final Long EXIST_REPORT_ID = 1L;
    private static final Long NOT_EXIST_REPORT_ID = 12L;

    private final CheatingTypeRepository cheatingTypeRepository = mock(CheatingTypeRepository.class);
    private final IngameServerRepository ingameServerRepository = mock(IngameServerRepository.class);
    private final UserRepository userRepository = mock(UserRepository.class);
    private final ReportRepository reportRepository = mock(ReportRepository.class);
    private final EvidenceRepository evidenceRepository = mock(EvidenceRepository.class);
    private final S3Uploader s3Uploader = mock(S3Uploader.class);

    private ReportService reportService;

    @BeforeEach
    void setUp() {
        reportService = new ReportService(
                cheatingTypeRepository,
                ingameServerRepository,
                userRepository,
                reportRepository,
                evidenceRepository,
                s3Uploader);

        given(cheatingTypeRepository.findById(EXIST_CHEATING_TYPE))
                .willReturn(Optional.of(new CheatingType("?????? ??????")));
        given(cheatingTypeRepository.findById(NOT_EXIST_CHEATING_TYPE))
                .willReturn(Optional.empty());

        given(ingameServerRepository.findById(EXIST_SERVER))
                .willReturn(Optional.of(new IngameServer("?????????")));
        given(ingameServerRepository.findById(NOT_EXIST_SERVER))
                .willReturn(Optional.empty());

        given(userRepository.findById(EXIST_USER))
                .willReturn(Optional.of(new User()));
        given(userRepository.findById(NOT_EXIST_USER))
                .willReturn(Optional.empty());

        given(reportRepository.save(any())).willReturn(Report.builder()
                .id(EXIST_REPORT_ID)
                .user(User.builder()
                        .email("email@email.com")
                        .nickname("nickname")
                        .build())
                .cheatingType(new CheatingType(1L, "?????? ??????"))
                .ingameServer(new IngameServer(1L, "?????????"))
                .ingameNickname("cheater")
                .cheatingDatetime(LocalDateTime.now())
                .status(ReportStatus.ACCEPTED)
                .build());

        List<ReportPreviewResponseData> reports = new ArrayList<>();
        IntStream.range(PAGE_INDEX, PAGE_SIZE).forEach(each -> {
            reportRepository.save(new Report());
            reports.add(new ReportPreviewResponseData());
        });

        Page<ReportPreviewResponseData> pagedReport = new PageImpl<>(reports);

        given(reportRepository.findAllDTO(PageRequest.of(PAGE_INDEX, PAGE_SIZE)))
                .willReturn(pagedReport);

        given(reportRepository.findByIdDTO(EXIST_REPORT_ID))
                .willReturn(Optional.of(ReportDetailResponseData.builder().reportId(EXIST_REPORT_ID).build()));

        given(reportRepository.findByIdDTO(NOT_EXIST_REPORT_ID))
                .willReturn(Optional.empty());

        given(reportRepository.findById(EXIST_REPORT_ID))
                .willReturn(Optional.of(Report.builder()
                        .id(EXIST_REPORT_ID)
                        .user(User.builder()
                            .email("email@email.com")
                            .nickname("nickname")
                            .build())
                        .cheatingType(new CheatingType(1L, "?????? ??????"))
                        .ingameServer(new IngameServer(1L, "?????????"))
                        .ingameNickname("cheater")
                        .cheatingDatetime(LocalDateTime.now())
                        .status(ReportStatus.ACCEPTED)
                        .build()));

        given(reportRepository.findById(NOT_EXIST_REPORT_ID))
                .willReturn(Optional.empty());
    }

    @Test
    @DisplayName("????????? ?????? - ??????")
    void addReport() {
        AddReportRequestData request = AddReportRequestData.builder()
                .ingameServer(1L)
                .situation("?????? ??????")
                .cheatingDatetime(LocalDateTime.now())
                .ingameNickname("cheater")
                .cheatingType(1L)
                .userId(1L)
                .build();

        AddReportResponseData response = reportService.addReport(request, EXIST_USER);

        assertAll(
                () -> assertEquals("cheater", response.getIngameNickname()),
                () -> assertEquals("?????????", response.getIngameServer()),
                () -> assertEquals("?????? ??????", response.getCheatingType())
        );
    }

    @Test
    @DisplayName("????????? ?????? - ?????? - ?????? ??????")
    void addReport_fail_not_valid_user() {
        AddReportRequestData request = AddReportRequestData.builder()
                .situation("?????? ??????")
                .ingameNickname("cheater")
                .cheatingDatetime(LocalDateTime.now())
                .ingameServer(EXIST_SERVER)
                .cheatingType(EXIST_CHEATING_TYPE)
                .userId(EXIST_USER)
                .build();

        UnauthorizedException exception = assertThrows(UnauthorizedException.class,
                () -> reportService.addReport(request, INVALID_USER));

        assertNotNull(exception.getMessage());
    }

    @ParameterizedTest
    @DisplayName("????????? ?????? - ?????? - ???????????? ?????? ??????")
    @MethodSource("paramsForAddReportFailInputs")
    void addReport_fail_not_exist_server(Long ingameServer, Long cheatingType, Long userId) {
        AddReportRequestData request = AddReportRequestData.builder()
                .situation("?????? ??????")
                .ingameNickname("cheater")
                .cheatingDatetime(LocalDateTime.now())
                .ingameServer(ingameServer)
                .cheatingType(cheatingType)
                .userId(userId)
                .build();

        IllegalDataException exception = assertThrows(IllegalDataException.class,
                () -> reportService.addReport(request, EXIST_USER));

        assertNotNull(exception.getMessage());
    }

    @Test
    @DisplayName("getReports - ??????")
    void getReports_success() {
        PageRequest pageRequest = PageRequest.of(PAGE_INDEX, PAGE_SIZE);

        Page<ReportPreviewResponseData> response = reportService.getReports(pageRequest);
        assertNotNull(response);
    }

    private static Stream<Arguments> paramsForAddReportFailInputs() {
        return Stream.of(
                Arguments.of(NOT_EXIST_SERVER, EXIST_CHEATING_TYPE, EXIST_USER),
                Arguments.of(EXIST_SERVER, NOT_EXIST_CHEATING_TYPE, EXIST_USER)
        );
    }

    @Test
    @DisplayName("getReport - ?????? - ?????? ??????")
    void getReport_success() {
        ReportDetailResponseData report = reportService.getReport(EXIST_REPORT_ID);

        assertNotNull(report);
    }

    @Test
    @DisplayName("getReport - ?????? - ???????????? ?????? ?????????")
    void getReport_fail() {
        IllegalDataException exception = assertThrows(IllegalDataException.class,
                () -> reportService.getReport(NOT_EXIST_REPORT_ID));

        assertNotNull(exception.getMessage());
    }

    @Test
    @DisplayName("updateStatus - ??????")
    void updateStatus() {
        UpdateReportStatusResponseData report = reportService.updateStatus(
                new UpdateReportStatusRequestData(false),
                EXIST_REPORT_ID);

        assertEquals(ReportStatus.ACCEPTED, report.getStatus()); // Mocking ??? ????????? ????????? ?????? ????????? PENDING ?????? ?????????

        report = reportService.updateStatus(new UpdateReportStatusRequestData(true), EXIST_REPORT_ID);
        assertEquals(ReportStatus.ACCEPTED, report.getStatus()); // Mocking ??? ????????? ????????? ?????? ????????? PENDING ?????? ?????????
    }

    @Test
    @DisplayName("updateStatus - ?????? - ???????????? ?????? ?????????")
    void updateStatus_fail() {
        IllegalDataException exception = assertThrows(IllegalDataException.class,
                () -> reportService.updateStatus(new UpdateReportStatusRequestData(), NOT_EXIST_REPORT_ID));

        assertNotNull(exception.getMessage());
    }
}