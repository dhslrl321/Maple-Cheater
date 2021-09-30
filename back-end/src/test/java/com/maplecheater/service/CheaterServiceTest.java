package com.maplecheater.service;

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.mock;

class CheaterServiceTest {

    private static final String EXIST_NICKNAME = "exist_cheater";
    private static final String NOT_EXIST_NICKNAME = "not_exist";

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

        given(cheaterRepository.findByIngameNickname(EXIST_NICKNAME))
                .willReturn(Optional.of(cheater));

        given(cheaterDetailRepository.findAllByCheaterNickname(EXIST_NICKNAME))
                .willReturn(List.of(cheaterDetail1, cheaterDetail2));

        willThrow(new CheaterNotFoundException(NOT_EXIST_NICKNAME))
                .given(cheaterRepository)
                .findByIngameNickname(NOT_EXIST_NICKNAME);
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

}