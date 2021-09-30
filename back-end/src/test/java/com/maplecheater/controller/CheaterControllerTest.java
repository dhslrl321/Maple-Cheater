package com.maplecheater.controller;

import com.maplecheater.domain.dto.response.SearchCheaterResponseData;
import com.maplecheater.domain.entity.Role;
import com.maplecheater.domain.type.RoleType;
import com.maplecheater.exception.CheaterNotFoundException;
import com.maplecheater.service.AuthenticationService;
import com.maplecheater.service.CheaterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CheaterController.class)
class CheaterControllerTest {

    private static final String EXIST_CHEAtER_NICKNAME = "exist_cheater";
    private static final String NOT_EXIST_CHEAtER_NICKNAME = "not_exist_cheater";

    private static final String VALID_TOKEN = "eyJhbGciOiJIUzI1NiJ" +
            "9.eyJ1c2VySWQiOjEsImV4cCI6MTYzMzMxNzI5NH0" +
            ".ZKq5d0v8F4n0Er1XhTuTfFQ6pzUHPnOi3D79YaCV83k";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CheaterService cheaterService;

    @MockBean
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {

        given(authenticationService.parseToken(VALID_TOKEN))
                .willReturn(1L);
        given(authenticationService.getRoles(1L))
                .willReturn(Arrays.asList(new Role(1L, RoleType.USER)));

        given(cheaterService.getCheater(EXIST_CHEAtER_NICKNAME))
                .willReturn(new SearchCheaterResponseData());

        willThrow(new CheaterNotFoundException(NOT_EXIST_CHEAtER_NICKNAME))
                .given(cheaterService)
                .getCheater(NOT_EXIST_CHEAtER_NICKNAME);
    }

    @Test
    @DisplayName("치터 검색 - 성공 - 존재하는 치터")
    void search_success_exist_cheater() throws Exception {
        mockMvc.perform(get("/api/v1/cheaters/{ingameNickname}", EXIST_CHEAtER_NICKNAME)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + VALID_TOKEN))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("치터 검색 - 성공 - 존재하지 않는 치터")
    void search_success_not_exist_cheater() throws Exception {
        mockMvc.perform(get("/api/v1/cheaters/{ingameNickname}", NOT_EXIST_CHEAtER_NICKNAME)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + VALID_TOKEN))
                .andDo(print())
                .andExpect(status().isAccepted());
    }
}