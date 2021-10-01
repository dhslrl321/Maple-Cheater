package com.maplecheater.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maplecheater.domain.dto.request.AddCheaterRequestData;
import com.maplecheater.domain.dto.response.SearchCheaterResponseData;
import com.maplecheater.domain.entity.CheaterDetail;
import com.maplecheater.domain.entity.Role;
import com.maplecheater.domain.type.RoleType;
import com.maplecheater.exception.CheaterNotFoundException;
import com.maplecheater.exception.IllegalDataException;
import com.maplecheater.service.AuthenticationService;
import com.maplecheater.service.CheaterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CheaterController.class)
class CheaterControllerTest {

    private static final Long EXIST_INGAME_SERVER = 1L;
    private static final Long EXIST_CHEATING_TYPE = 1L;

    private static final Long NOT_EXIST_INGAME_SERVER = 22L;

    private static final String EXIST_CHEAtER_NICKNAME = "exist_cheater";
    private static final String NOT_EXIST_CHEAtER_NICKNAME = "not_exist_cheater";

    private static final String VALID_TOKEN = "eyJhbGciOiJIUzI1NiJ" +
            "9.eyJ1c2VySWQiOjEsImV4cCI6MTYzMzMxNzI5NH0" +
            ".ZKq5d0v8F4n0Er1XhTuTfFQ6pzUHPnOi3D79YaCV83k";

    private static final String ADMIN_TOKEN = "eyJhbGciOiJIUzI1NiJ" +
            "9.eyJ1c2VySWQiOjEsXdEvDcCI6MTYzMzMxNzI5NH0" +
            ".ZKq5d0v8F4n0Er1XhTuTfFQ6pzUHPnOi3D79YaCV83k";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

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

        given(authenticationService.parseToken(ADMIN_TOKEN))
                .willReturn(777L);
        given(authenticationService.getRoles(777L))
                .willReturn(Arrays.asList(new Role(777L, RoleType.USER), new Role(777L, RoleType.ADMIN)));

        given(cheaterService.getCheater(EXIST_CHEAtER_NICKNAME))
                .willReturn(new SearchCheaterResponseData());

        willThrow(new CheaterNotFoundException(NOT_EXIST_CHEAtER_NICKNAME))
                .given(cheaterService)
                .getCheater(NOT_EXIST_CHEAtER_NICKNAME);

        given(cheaterService.addCheater(any())).will(invocation -> {
            AddCheaterRequestData request = invocation.getArgument(0);
            if(request.getIngameServer().equals(NOT_EXIST_INGAME_SERVER)) {
                throw new IllegalDataException("");
            }
            return new CheaterDetail();
        });
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

    @Test
    @DisplayName("치터 생성 - 성공")
    void addCheater() throws Exception {
        AddCheaterRequestData request = AddCheaterRequestData.builder()
                .ingameNickname(EXIST_CHEAtER_NICKNAME)
                .ingameServer(EXIST_INGAME_SERVER)
                .cheatingType(EXIST_CHEATING_TYPE)
                .cheatingDatetime(LocalDateTime.now())
                .build();

        mockMvc.perform(post("/api/v1/cheaters")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + ADMIN_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("치터 생성 - 실패 - 일반 사용자 접근")
    void addCheater_fail_user_access() throws Exception {
        AddCheaterRequestData request = AddCheaterRequestData.builder()
                .ingameNickname(EXIST_CHEAtER_NICKNAME)
                .ingameServer(NOT_EXIST_INGAME_SERVER)
                .cheatingType(EXIST_CHEATING_TYPE)
                .cheatingDatetime(LocalDateTime.now())
                .build();

        mockMvc.perform(post("/api/v1/cheaters")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + VALID_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("치터 생성 - 서버 존재하지 않음")
    void addCheater_fail_not_exist_server() throws Exception {
        AddCheaterRequestData request = AddCheaterRequestData.builder()
                .ingameNickname(EXIST_CHEAtER_NICKNAME)
                .ingameServer(NOT_EXIST_INGAME_SERVER)
                .cheatingType(EXIST_CHEATING_TYPE)
                .cheatingDatetime(LocalDateTime.now())
                .build();

        mockMvc.perform(post("/api/v1/cheaters")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + ADMIN_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}