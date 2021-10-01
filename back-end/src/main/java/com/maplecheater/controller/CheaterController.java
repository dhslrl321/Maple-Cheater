package com.maplecheater.controller;

import com.maplecheater.domain.dto.request.AddCheaterRequestData;
import com.maplecheater.domain.dto.response.SearchCheaterResponseData;
import com.maplecheater.domain.entity.CheaterDetail;
import com.maplecheater.security.UserAuthentication;
import com.maplecheater.service.CheaterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/cheaters", produces = "application/json")
public class CheaterController {
    private final CheaterService cheaterService;

    @GetMapping("/{ingameNickname}")
    @PreAuthorize("isAuthenticated() and hasAuthority('USER')")
    public ResponseEntity<SearchCheaterResponseData> search(@PathVariable String ingameNickname) {
        return ResponseEntity.ok(cheaterService.getCheater(ingameNickname));
    }

    @PostMapping
    @PreAuthorize("isAuthenticated() and hasAuthority('ADMIN')")
    public ResponseEntity<CheaterDetail> addCheater(@RequestBody AddCheaterRequestData request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cheaterService.addCheater(request));
    }
}
