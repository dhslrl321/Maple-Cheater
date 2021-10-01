package com.maplecheater.service;

import com.maplecheater.domain.dto.request.AddCheaterRequestData;
import com.maplecheater.domain.dto.response.SearchCheaterResponseData;
import com.maplecheater.domain.entity.Cheater;
import com.maplecheater.domain.entity.CheaterDetail;
import com.maplecheater.domain.entity.CheatingType;
import com.maplecheater.domain.entity.IngameServer;
import com.maplecheater.domain.repository.cheater.CheaterRepository;
import com.maplecheater.domain.repository.cheaterdetail.CheaterDetailRepository;
import com.maplecheater.domain.repository.cheatingtype.CheatingTypeRepository;
import com.maplecheater.domain.repository.ingameserver.IngameServerRepository;
import com.maplecheater.exception.AuthenticationFailedException;
import com.maplecheater.exception.CheaterNotFoundException;
import com.maplecheater.exception.IllegalDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.mock;

class CheaterServiceTest {

    private static final String EXIST_NICKNAME = "exist_cheater";
    private static final String NOT_EXIST_NICKNAME = "not_exist";

    private static final Long EXIST_INGAME_SERVER = 1L;
    private static final Long EXIST_CHEATING_TYPE = 1L;

    private static final Long NOT_EXIST_INGAME_SERVER = 22L;
    private static final Long NOT_EXIST_CHEATING_TYPE = 22L;

    private CheaterRepository cheaterRepository = mock(CheaterRepository.class);
    private CheaterDetailRepository cheaterDetailRepository = mock(CheaterDetailRepository.class);
    private IngameServerRepository ingameServerRepository = mock(IngameServerRepository.class);
    private CheatingTypeRepository cheatingTypeRepository = mock(CheatingTypeRepository.class);

    private CheaterService cheaterService;

    @BeforeEach
    void setUp() {
        cheaterService = new CheaterService(cheaterRepository,
                cheaterDetailRepository,
                ingameServerRepository,
                cheatingTypeRepository);

        cheaterRepository.findByIngameNickname(EXIST_NICKNAME);
        cheaterDetailRepository.findAllByCheaterNickname(EXIST_NICKNAME);

        Cheater cheater = Cheater.builder()
                .ingameNickname(EXIST_NICKNAME)
                .ingameServer(new IngameServer("크로아"))
                .build();

        CheaterDetail cheaterDetail1 = CheaterDetail.builder()
                .cheater(cheater)
                .cheatingType(new CheatingType("현금 거래"))
                .build();

        CheaterDetail cheaterDetail2 = CheaterDetail.builder()
                .cheater(cheater)
                .cheatingType(new CheatingType("현금 거래"))
                .build();

        given(ingameServerRepository.findById(EXIST_INGAME_SERVER))
                .willReturn(Optional.of(new IngameServer(1L, "크로아")));

        given(ingameServerRepository.findById(NOT_EXIST_INGAME_SERVER))
                .willReturn(Optional.empty());

        given(cheatingTypeRepository.findById(EXIST_CHEATING_TYPE))
                .willReturn(Optional.of(new CheatingType(1L, "현금 거래")));

        given(cheatingTypeRepository.findById(NOT_EXIST_CHEATING_TYPE))
                .willReturn(Optional.empty());

        given(cheaterRepository.findByIngameNickname(EXIST_NICKNAME))
                .willReturn(Optional.of(cheater));

        given(cheaterDetailRepository.findAllByCheaterNickname(EXIST_NICKNAME))
                .willReturn(List.of(cheaterDetail1, cheaterDetail2));

        given(cheaterRepository.findByIngameNickname(NOT_EXIST_NICKNAME))
                .willReturn(Optional.empty());

    }

    @Test
    @DisplayName("getCheater - 성공")
    void getCheater_success() {
        SearchCheaterResponseData response = cheaterService.getCheater(EXIST_NICKNAME);
        assertEquals(2, response.getCheaterDetails().size());
    }

    @Test
    @DisplayName("getCheater - 성공 - 존재하지 않는 치터")
    void getCheater_success_no_cheater() {
        CheaterNotFoundException exception = assertThrows(CheaterNotFoundException.class,
                () -> cheaterService.getCheater(NOT_EXIST_NICKNAME));

        assertNotNull(exception.getMessage());
    }

    @Test
    @DisplayName("save - 성공")
    void saveCheater_success() {
        AddCheaterRequestData request = AddCheaterRequestData.builder()
                .ingameNickname(EXIST_NICKNAME)
                .ingameServer(1L)
                .cheatingType(1L)
                .cheatingDatetime(LocalDateTime.now())
                .build();

        assertDoesNotThrow(() -> cheaterService.addCheater(request));
    }

    @Test
    @DisplayName("save - 성공 - 이미 존재하는 치터")
    void saveCheater_success_exist() {
        AddCheaterRequestData request = AddCheaterRequestData.builder()
                .ingameNickname(EXIST_NICKNAME)
                .ingameServer(1L)
                .cheatingType(1L)
                .cheatingDatetime(LocalDateTime.now())
                .build();

        assertDoesNotThrow(() -> cheaterService.addCheater(request));
    }

    @Test
    @DisplayName("save - 성공 - 존재하지 않는 캐릭터")
    void saveCheater_success_not_exist() {
        AddCheaterRequestData request = AddCheaterRequestData.builder()
                .ingameNickname(NOT_EXIST_NICKNAME)
                .ingameServer(1L)
                .cheatingType(1L)
                .cheatingDatetime(LocalDateTime.now())
                .build();

        assertDoesNotThrow(() -> cheaterService.addCheater(request));
    }

    @Test
    @DisplayName("save - 실패 - 존재하지 않는 서버")
    void saveCheater_fail_not_exist_server() {
        AddCheaterRequestData request = AddCheaterRequestData.builder()
                .ingameNickname(EXIST_NICKNAME)
                .ingameServer(NOT_EXIST_INGAME_SERVER)
                .cheatingType(EXIST_CHEATING_TYPE)
                .cheatingDatetime(LocalDateTime.now())
                .build();

        IllegalDataException exception = assertThrows(IllegalDataException.class,
                () -> cheaterService.addCheater(request));

        assertNotNull(exception.getMessage());
    }

    @Test
    @DisplayName("save - 실패 - 존재하지 않는 치팅 타입")
    void saveCheater_fail_not_exist_cheating_type() {
        AddCheaterRequestData request = AddCheaterRequestData.builder()
                .ingameNickname(EXIST_NICKNAME)
                .ingameServer(EXIST_INGAME_SERVER)
                .cheatingType(NOT_EXIST_CHEATING_TYPE)
                .cheatingDatetime(LocalDateTime.now())
                .build();

        IllegalDataException exception = assertThrows(IllegalDataException.class,
                () -> cheaterService.addCheater(request));

        assertNotNull(exception.getMessage());
    }

}