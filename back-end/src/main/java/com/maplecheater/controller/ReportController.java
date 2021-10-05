package com.maplecheater.controller;

import com.maplecheater.domain.dto.request.AddReportRequestData;
import com.maplecheater.domain.dto.request.UpdateReportStatusRequestData;
import com.maplecheater.domain.dto.response.*;
import com.maplecheater.domain.entity.Report;
import com.maplecheater.security.UserAuthentication;
import com.maplecheater.service.EvidenceService;
import com.maplecheater.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/reports", produces = "application/json")
public class ReportController {
    private final ReportService reportService;

    /**
    @PostMapping
    @PreAuthorize("isAuthenticated() and hasAnyAuthority('USER')")
    public ResponseEntity<AddReportResponseData> addReport(@RequestBody AddReportRequestData request,
                                                           UserAuthentication authentication) {
        Long tokenUserId = authentication.getUserid();
        return ResponseEntity.status(HttpStatus.CREATED).body(reportService.addReport(request, tokenUserId));
    }*/

    @PostMapping
    @PreAuthorize("isAuthenticated() and hasAnyAuthority('USER')")
    public ResponseEntity<AddReportResponseData> addReport2(@RequestParam("ingameNickname") String nickname,
                                                           @RequestParam("year") Integer year,
                                                           @RequestParam("month") Integer month,
                                                           @RequestParam("day") Integer day,
                                                           @RequestParam("situation") String situation,
                                                           @RequestParam("userId") Long userId,
                                                           @RequestParam("ingameServer") Long ingameServer,
                                                           @RequestParam("cheatingType") Long cheatingType,
                                                           @RequestParam("images") List<MultipartFile> images,
                                                           UserAuthentication authentication) throws IOException {
        Long tokenUserId = authentication.getUserid();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reportService.addReport2(AddReportRequestData.builder()
                .ingameNickname(nickname)
                .situation(situation)
                .cheatingDatetime(LocalDateTime.of(year, month, day, 0, 0))
                .userId(userId)
                .ingameServer(ingameServer)
                .cheatingType(cheatingType)
                .build(), images, tokenUserId));
    }

    @GetMapping
    @PreAuthorize("isAuthenticated() and hasAnyAuthority('ADMIN')")
    public ResponseEntity<Page<ReportPreviewResponseData>> getReports(Pageable pageable) {
        return ResponseEntity.ok(reportService.getReports(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated() and hasAnyAuthority('ADMIN')")
    public ResponseEntity<ReportDetailResponseData> getReport(@PathVariable Long id) {
        return ResponseEntity.ok(reportService.getReport(id));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("isAuthenticated() and hasAnyAuthority('ADMIN')")
    public ResponseEntity<UpdateReportStatusResponseData> updateReportStatus(
            @RequestBody UpdateReportStatusRequestData request,
            @PathVariable Long id) {
        return ResponseEntity.ok(reportService.updateStatus(request, id));
    }
}
