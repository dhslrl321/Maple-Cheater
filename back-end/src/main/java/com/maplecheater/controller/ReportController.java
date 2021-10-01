package com.maplecheater.controller;

import com.maplecheater.domain.dto.request.AddCheaterRequestData;
import com.maplecheater.domain.dto.request.AddReportRequestData;
import com.maplecheater.domain.dto.response.AddReportResponseData;
import com.maplecheater.security.UserAuthentication;
import com.maplecheater.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/reports", produces = "application/json")
public class ReportController {
    private final ReportService reportService;

    @PostMapping
    @PreAuthorize("isAuthenticated() and hasAuthority('USER')")
    public ResponseEntity<AddReportResponseData> addReport(@RequestBody AddReportRequestData request,
                                                           UserAuthentication authentication) {
        Long tokenUserId = authentication.getUserid();
        return ResponseEntity.status(HttpStatus.CREATED).body(reportService.addReport(request, tokenUserId));
    }
}
