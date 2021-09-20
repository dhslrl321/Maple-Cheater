package com.maplecheater.controller;

import com.maplecheater.domain.dto.request.RegisterRequestData;
import com.maplecheater.domain.dto.response.RegisterResponseData;
import com.maplecheater.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/users", produces = "application/json")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<RegisterResponseData> register(@RequestBody RegisterRequestData registerRequestData) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerUser(registerRequestData));
    }
}
