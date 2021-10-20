package com.maplecheater.controller;

import com.maplecheater.domain.dto.request.ChangeNicknameRequestData;
import com.maplecheater.domain.dto.request.ChangePasswordRequestData;
import com.maplecheater.domain.dto.request.RegisterRequestData;
import com.maplecheater.domain.dto.response.EmailCheckResponseData;
import com.maplecheater.domain.dto.response.RegisterResponseData;
import com.maplecheater.domain.dto.response.ReportDetailResponseData;
import com.maplecheater.domain.dto.response.ReportPreviewResponseData;
import com.maplecheater.security.UserAuthentication;
import com.maplecheater.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/users", produces = "application/json")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<RegisterResponseData> register(@RequestBody @Valid  RegisterRequestData registerRequestData) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerUser(registerRequestData));
    }

    @GetMapping("/exists/{email}")
    public ResponseEntity<EmailCheckResponseData> checkEmail(@PathVariable("email") String email) {
        return ResponseEntity.ok(userService.isExistEmail(email));
    }

    @PatchMapping("/{id}/password")
    @PreAuthorize("isAuthenticated() and hasAuthority('USER')")
    public ResponseEntity changePassword(@PathVariable("id") Long targetId,
            @RequestBody ChangePasswordRequestData changePasswordRequestData,
            UserAuthentication authentication) {

        Long tokenUserId = authentication.getUserid();

        userService.changePassword(targetId, changePasswordRequestData, tokenUserId);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/nickname")
    @PreAuthorize("isAuthenticated() and hasAuthority('USER')")
    public ResponseEntity changeNickname(@PathVariable("id") Long targetId,
                                         @RequestBody ChangeNicknameRequestData changeNicknameRequestData,
                                         UserAuthentication authentication) {
        Long tokenUserId = authentication.getUserid();

        userService.changeNickname(targetId, changeNicknameRequestData, tokenUserId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated() and hasAuthority('USER')")
    public ResponseEntity unregister(@PathVariable("id") Long targetId,
                                     UserAuthentication authentication) {
        Long tokenUserId = authentication.getUserid();

        userService.unregister(targetId, tokenUserId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/reports")
    @PreAuthorize("isAuthenticated() and hasAuthority('USER')")
    public ResponseEntity<Page<ReportPreviewResponseData>> getAllMyReports(@PathVariable("id") Long userId,
                                                                           UserAuthentication authentication,
                                                                           Pageable pageable) {
        Long tokenUserId = authentication.getUserid();
        return ResponseEntity.ok(userService.getAllReports(pageable, userId, tokenUserId));
    }

    @GetMapping("/{userId}/reports/{reportId}")
    @PreAuthorize("isAuthenticated() and hasAuthority('USER')")
    public ResponseEntity<ReportDetailResponseData> getReport(@PathVariable Long userId,
                                                              @PathVariable Long reportId,
                                                              UserAuthentication authentication) {
        Long tokenUserId = authentication.getUserid();
        return ResponseEntity.ok(userService.getReport(reportId, userId, tokenUserId));
    }
}
