package com.maplecheater.service;

import com.maplecheater.domain.dto.request.AddReportRequestData;
import com.maplecheater.domain.dto.response.AddReportResponseData;
import com.maplecheater.domain.entity.CheatingType;
import com.maplecheater.domain.entity.IngameServer;
import com.maplecheater.domain.entity.Report;
import com.maplecheater.domain.entity.User;
import com.maplecheater.domain.repository.cheatingtype.CheatingTypeRepository;
import com.maplecheater.domain.repository.ingameserver.IngameServerRepository;
import com.maplecheater.domain.repository.report.ReportRepository;
import com.maplecheater.domain.repository.user.UserRepository;
import com.maplecheater.exception.IllegalDataException;
import com.maplecheater.exception.UnauthorizedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.Optional;
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

    private final CheatingTypeRepository cheatingTypeRepository = mock(CheatingTypeRepository.class);
    private final IngameServerRepository ingameServerRepository = mock(IngameServerRepository.class);
    private final UserRepository userRepository = mock(UserRepository.class);
    private final ReportRepository reportRepository = mock(ReportRepository.class);

    private ReportService reportService;

    @BeforeEach
    void setUp() {
        reportService = new ReportService(
                cheatingTypeRepository,
                ingameServerRepository,
                userRepository,
                reportRepository);

        given(cheatingTypeRepository.findById(EXIST_CHEATING_TYPE))
                .willReturn(Optional.of(new CheatingType("현금 거래")));
        given(cheatingTypeRepository.findById(NOT_EXIST_CHEATING_TYPE))
                .willReturn(Optional.empty());

        given(ingameServerRepository.findById(EXIST_SERVER))
                .willReturn(Optional.of(new IngameServer("크로아")));
        given(ingameServerRepository.findById(NOT_EXIST_SERVER))
                .willReturn(Optional.empty());

        given(userRepository.findById(EXIST_USER))
                .willReturn(Optional.of(new User()));
        given(userRepository.findById(NOT_EXIST_USER))
                .willReturn(Optional.empty());

        given(reportRepository.save(any())).willReturn(Report.builder()
                .ingameNickname("cheater")
                .cheatingType(new CheatingType("현금 거래"))
                .ingameServer(new IngameServer("크로아"))
                .build());
    }

    @Test
    @DisplayName("신고서 저장 - 성공")
    void addReport() {
        AddReportRequestData request = AddReportRequestData.builder()
                .ingameServer(1L)
                .situation("문제 상황")
                .cheatingDatetime(LocalDateTime.now())
                .ingameNickname("cheater")
                .cheatingType(1L)
                .userId(1L)
                .build();

        AddReportResponseData response = reportService.addReport(request, EXIST_USER);

        assertAll(
                () -> assertEquals("cheater", response.getIngameNickname()),
                () -> assertEquals("크로아", response.getIngameServer()),
                () -> assertEquals("현금 거래", response.getCheatingType())
        );
    }

    @Test
    @DisplayName("신고서 저장 - 실패 - 인증 오류")
    void addReport_fail_not_valid_user() {
        AddReportRequestData request = AddReportRequestData.builder()
                .situation("문제 상황")
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
    @DisplayName("신고서 저장 - 실패 - 존재하지 않는 서버")
    @MethodSource("paramsForAddReportFailInputs")
    void addReport_fail_not_exist_server(Long ingameServer, Long cheatingType, Long userId) {
        AddReportRequestData request = AddReportRequestData.builder()
                .situation("문제 상황")
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

    private static Stream<Arguments> paramsForAddReportFailInputs() {
        return Stream.of(
                Arguments.of(NOT_EXIST_SERVER, EXIST_CHEATING_TYPE, EXIST_USER),
                Arguments.of(EXIST_SERVER, NOT_EXIST_CHEATING_TYPE, EXIST_USER)
        );
    }
}