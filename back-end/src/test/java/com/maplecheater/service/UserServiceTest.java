package com.maplecheater.service;

import com.maplecheater.domain.dto.request.RegisterRequestData;
import com.maplecheater.domain.dto.response.RegisterResponseData;
import com.maplecheater.domain.entity.EmailVerification;
import com.maplecheater.domain.entity.User;
import com.maplecheater.domain.repository.emailverification.EmailVerificationRepository;
import com.maplecheater.domain.repository.user.UserRepository;
import com.maplecheater.domain.type.VerificationType;
import com.maplecheater.exception.EmailNotFoundException;
import com.maplecheater.exception.InvalidVerificationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class UserServiceTest {

    private static final String EMAIL = "test@test.com";
    private static final String EMAIL_DOES_NOT_EXIST = "no@no.no";
    private static final String EMAIL_NOT_VERIFIED = "verify@verify.no";

    private static final String PASSWORD = "password";
    private static final String NICKNAME = "nickname";

    private UserRepository userRepository = mock(UserRepository.class);
    private EmailVerificationRepository emailVerificationRepository = mock(EmailVerificationRepository.class);
    private UserService userService;

    @BeforeEach
    void setUp() {
        ModelMapper modelMapper = new ModelMapper();

        userService = new UserService(modelMapper, userRepository, emailVerificationRepository);

        User user = User.builder()
                .id(1L)
                .email(EMAIL)
                .password(PASSWORD)
                .nickname(NICKNAME)
                .registeredAt(LocalDateTime.now())
                .build();

        EmailVerification emailVerification = EmailVerification.builder()
                .id(1L)
                .email(EMAIL)
                .code("1A2B3C")
                .verified(VerificationType.UNVERIFIED)
                .build();

        given(userRepository.save(any(User.class))).willReturn(user);

        given(userRepository.findByEmail(EMAIL)).willReturn(user);
        given(userRepository.findByEmail(EMAIL_NOT_VERIFIED)).willReturn(user);

        given(emailVerificationRepository.findByEmail(EMAIL)).willReturn(emailVerification);
        given(emailVerificationRepository.findByEmail(EMAIL_NOT_VERIFIED)).willReturn(emailVerification);

        given(emailVerificationRepository.findVerifiedByEmail(EMAIL)).willReturn(VerificationType.VERIFIED);
        given(emailVerificationRepository.findVerifiedByEmail(EMAIL_NOT_VERIFIED)).willReturn(VerificationType.UNVERIFIED);
    }

    @Test
    @DisplayName("이메일 인증을 확인하는 테스트 - 성공")
    void checkVerifiedEmail_success() {
        boolean isVerified = userService.checkVerifiedEmail(EMAIL);

        assertTrue(isVerified);
    }

    @Test
    @DisplayName("이메일 인증을 확인하는 테스트 - 실패 - 존재하지 않는 이메일")
    void checkVerifiedEmail_fail_doesnt_exist_email() {
        EmailNotFoundException emailNotFoundException = assertThrows(EmailNotFoundException.class, () ->
                userService.checkVerifiedEmail(EMAIL_DOES_NOT_EXIST));

        assertNotNull(emailNotFoundException.getMessage());
    }

    @Test
    @DisplayName("회원 가입 - 성공")
    void registerUser_success() {
        RegisterRequestData request = RegisterRequestData.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .nickname(NICKNAME)
                .build();

        RegisterResponseData response = userService.registerUser(request);

        assertEquals(response.getEmail(), EMAIL);
        assertEquals(response.getNickname(), NICKNAME);
    }

    @Test
    @DisplayName("회원 가입 - 실패 - 인증받지 않은 사용자")
    void registerUser_fail_insufficient_authorization() {
        RegisterRequestData request = RegisterRequestData.builder()
                .email(EMAIL_NOT_VERIFIED)
                .password(PASSWORD)
                .nickname(NICKNAME)
                .build();

        InvalidVerificationException invalidVerificationException = assertThrows(InvalidVerificationException.class,
                () -> userService.registerUser(request));

        assertNotNull(invalidVerificationException.getMessage());
    }
}