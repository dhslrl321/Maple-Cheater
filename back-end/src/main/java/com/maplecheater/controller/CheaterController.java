package com.maplecheater.controller;

import com.maplecheater.domain.dto.response.SearchCheaterResponseData;
import com.maplecheater.security.UserAuthentication;
import com.maplecheater.service.CheaterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
