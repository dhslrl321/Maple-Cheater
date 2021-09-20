package com.maplecheater.controller;

import com.maplecheater.domain.dto.request.LoginRequestData;
import com.maplecheater.domain.dto.response.LoginResponseData;
import com.maplecheater.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/authenticate", produces = "application/json")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<LoginResponseData> login(@RequestBody LoginRequestData loginRequestData) {
        String accessToken = authenticationService.login(loginRequestData);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new LoginResponseData(accessToken));
    }
}
