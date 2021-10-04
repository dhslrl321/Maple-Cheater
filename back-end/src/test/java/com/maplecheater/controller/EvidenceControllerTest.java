package com.maplecheater.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maplecheater.domain.dto.request.AddReportRequestData;
import com.maplecheater.domain.dto.response.AddReportResponseData;
import com.maplecheater.domain.dto.response.EvidenceImageResponseData;
import com.maplecheater.domain.dto.response.ReportDetailResponseData;
import com.maplecheater.domain.dto.response.ReportPreviewResponseData;
import com.maplecheater.domain.entity.Role;
import com.maplecheater.domain.type.RoleType;
import com.maplecheater.exception.IllegalDataException;
import com.maplecheater.service.AuthenticationService;
import com.maplecheater.service.EvidenceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EvidenceController.class)
class EvidenceControllerTest {

    private static final Long USER_ID = 1L;

    private static final String  USER_TOKEN = "eyJhbGciOiJIUzI1NiJ" +
            "9.eyJ1c2VySWQiOjEsImV4cCI6MTYzMzMxNzI5NH0" +
            ".ZKq5d0v8F4n0Er1XhTuTfFQ6pzUHPnOi3D79YaCV83k";

    private static final String INVALID_TOKEN = "eyJhbGciOiXIUzI1NiJ" +
            "9.eyJ1c2VySWQiOjEsImV4cCI6MTYzMzMxNzI5NH0" +
            ".ZKq5d0v8F4n0Er1XhTuTfFQ6pzUHPnOi3D79YaCV83k";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EvidenceService evidenceService;
    @MockBean
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        given(authenticationService.parseToken(USER_TOKEN))
                .willReturn(USER_ID);

        given(authenticationService.getRoles(USER_ID))
                .willReturn(Arrays.asList(new Role(USER_ID, RoleType.USER)));

        given(evidenceService.getImageUrl(1L))
                .willReturn(List.of(new EvidenceImageResponseData()));
    }

    @Test
    @DisplayName("이미지 조회 - 성공")
    void getImageUrls() throws Exception {
        mockMvc.perform(get("/api/v1/evidences")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + USER_TOKEN))
                .andDo(print())
                .andExpect(status().isOk());
    }

    /**
    @Test
    @DisplayName("이미지 저장 - 성공")
    void uploadImage() throws Exception {

        Resource fileResource = new ClassPathResource("YOUR FILE NAME");

        assertNotNull(fileResource);

        MockMultipartFile firstFile = new MockMultipartFile(
                "images",fileResource.getFilename(),
                MediaType.MULTIPART_FORM_DATA_VALUE,
                fileResource.getInputStream());

        mockMvc.perform(post("/api/v1/evidences")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .content(firstFile)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + USER_TOKEN))
                .andDo(print())
                .andExpect(status().isOk());
    }*/
}