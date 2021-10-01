package com.maplecheater.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maplecheater.domain.dto.request.AddReportRequestData;
import com.maplecheater.domain.dto.response.AddReportResponseData;
import com.maplecheater.domain.entity.Role;
import com.maplecheater.domain.type.RoleType;
import com.maplecheater.exception.IllegalDataException;
import com.maplecheater.service.AuthenticationService;
import com.maplecheater.service.ReportService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReportController.class)
class ReportControllerTest {

    private static final Long USER_ID = 1L;
    private static final Long EXIST_REQUEST_ID = 1L;
    private static final Long NOT_EXIST_REQUEST_ID = 2L;

    private static final String  USER_TOKEN = "eyJhbGciOiJIUzI1NiJ" +
            "9.eyJ1c2VySWQiOjEsImV4cCI6MTYzMzMxNzI5NH0" +
            ".ZKq5d0v8F4n0Er1XhTuTfFQ6pzUHPnOi3D79YaCV83k";

    private static final String ADMIN_TOKEN = "eyJhbGciOiJIUzI1NiJ" +
            "9.eyJ1c2VySWQiOjEsImV4cCI6MTYzMzMxNzI5NH0" +
            ".ZKq5d0v8F4n0Er1XhTuTfFQ6pzUHPnOi3D79YaCV83k";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReportService reportService;
    @MockBean
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        given(authenticationService.parseToken(USER_TOKEN))
                .willReturn(USER_ID);

        given(authenticationService.getRoles(USER_ID))
                .willReturn(Arrays.asList(new Role(USER_ID, RoleType.USER)));

        given(reportService.addReport(any(AddReportRequestData.class), any(Long.class))).will(invocation -> {
            AddReportRequestData argument = invocation.getArgument(0);

            Long ingameServer = argument.getIngameServer();

            if(ingameServer.equals(NOT_EXIST_REQUEST_ID)) {
                throw new IllegalDataException("");
            }

            return AddReportResponseData.builder().build();
        });
    }

    @Test
    @DisplayName("신고 생성 - 성공")
    void addReport_success() throws Exception {
        AddReportRequestData request = AddReportRequestData.builder()
                .userId(EXIST_REQUEST_ID)
                .ingameServer(EXIST_REQUEST_ID)
                .cheatingType(EXIST_REQUEST_ID)
                .ingameNickname("cheaters")
                .cheatingDatetime(LocalDateTime.now())
                .situation("situation")
                .build();

        mockMvc.perform(post("/api/v1/reports")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + USER_TOKEN))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("신고 생성 - 실패 - 잘못된 입력값")
    void addReport_fail_invalid_input() throws Exception {
        AddReportRequestData request = AddReportRequestData.builder()
                .userId(EXIST_REQUEST_ID)
                .cheatingType(EXIST_REQUEST_ID)
                .ingameServer(NOT_EXIST_REQUEST_ID)
                .ingameNickname("cheaters")
                .cheatingDatetime(LocalDateTime.now())
                .situation("situation")
                .build();

        mockMvc.perform(post("/api/v1/reports")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + USER_TOKEN))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}