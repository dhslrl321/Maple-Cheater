package com.maplecheater.controller;

import com.maplecheater.domain.dto.response.EvidenceImageResponseData;
import com.maplecheater.service.EvidenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/evidences", produces = "application/json")
public class EvidenceController {

    private final EvidenceService evidenceService;

    @GetMapping("/{reportId}")
    public ResponseEntity<List<EvidenceImageResponseData>> getImageUrls(@PathVariable Long reportId) {
        return ResponseEntity.ok(evidenceService.getImageUrl(reportId));
    }

    @PostMapping
    public ResponseEntity<List<EvidenceImageResponseData>> uploadToS3(List<MultipartFile> images) throws IOException {
        return ResponseEntity.ok(evidenceService.uploadToS3(images));
    }
}
