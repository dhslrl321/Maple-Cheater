package com.maplecheater.controller;

import com.maplecheater.domain.dto.request.LoginRequestData;
import com.maplecheater.domain.dto.response.LoginResponseData;
import com.maplecheater.service.AuthenticationService;
import com.maplecheater.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/authenticate", produces = "application/json")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final MailService mailService;

    @PostMapping
    public ResponseEntity<LoginResponseData> login(@RequestBody LoginRequestData loginRequestData) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authenticationService.login(loginRequestData));
    }

    @GetMapping("/{email}")
    public ResponseEntity sendAuthenticationCodeToMail(@PathVariable String email) {
        mailService.sendMail(email);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{email}/{code}")
    public ResponseEntity authenticate(@PathVariable String email,
                                       @PathVariable String code) {
        mailService.authenticate(email, code);
        return ResponseEntity.noContent().build();
    }
}
