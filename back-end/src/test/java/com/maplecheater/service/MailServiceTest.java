package com.maplecheater.service;

import com.maplecheater.domain.entity.EmailVerification;
import com.maplecheater.domain.entity.User;
import com.maplecheater.domain.repository.emailverification.EmailVerificationRepository;
import com.maplecheater.domain.repository.user.UserRepository;
import com.maplecheater.domain.type.VerificationType;
import com.maplecheater.exception.UserExistsException;
import com.maplecheater.util.MailUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class MailServiceTest {
    private static final String NEW_USER_EMAIL = "new@new.com";
    private static final String VERIFIED_USER_EMAIL = "verified@verified.com";
    private static final String VERIFIED_AND_SERVICE_USER_EMAIL = "service@service.com";

    private final JavaMailSender javaMailSender = mock(JavaMailSender.class);
    private final MailUtil mailUtil = mock(MailUtil.class);
    private final UserRepository userRepository = mock(UserRepository.class);
    private final EmailVerificationRepository emailVerificationRepository = mock(EmailVerificationRepository.class);

    private MailService mailService;

    @BeforeEach
    void setUp() {

        mailService = new MailService(emailVerificationRepository,
                userRepository,
                javaMailSender,
                mailUtil);

        User serviceUser = User.builder()
                .id(1L)
                .email(VERIFIED_AND_SERVICE_USER_EMAIL)
                .registeredAt(LocalDateTime.now().minusDays(2L))
                .build();

        EmailVerification existVerification = EmailVerification.builder()
                .id(1L)
                .email(VERIFIED_USER_EMAIL)
                .code("312213")
                .verified(VerificationType.UNVERIFIED)
                .build();

        EmailVerification serviceUsersVerification = EmailVerification.builder()
                .id(1L)
                .email(VERIFIED_AND_SERVICE_USER_EMAIL)
                .code("312213")
                .verified(VerificationType.UNVERIFIED)
                .build();

        given(emailVerificationRepository.findByEmail(NEW_USER_EMAIL))
                .willReturn(Optional.empty());

        given(emailVerificationRepository.findByEmail(VERIFIED_USER_EMAIL))
                .willReturn(Optional.of(existVerification));

        given(emailVerificationRepository.findByEmail(VERIFIED_USER_EMAIL))
                .willReturn(Optional.empty());

        given(emailVerificationRepository.findByEmail(VERIFIED_AND_SERVICE_USER_EMAIL))
                .willReturn(Optional.of(serviceUsersVerification));

        given(userRepository.existsAndNotUnregisteredByEmail(VERIFIED_AND_SERVICE_USER_EMAIL))
                .willReturn(true);
    }

    @Test
    @DisplayName("이메일 코드 발급 - 새로운 사용자")
    void verify_new_user() {
        assertDoesNotThrow(() -> mailService.sendMail(NEW_USER_EMAIL));
    }

    @Test
    @DisplayName("이메일 코드 발급 - 인증 받았던 이력이 있는 사용자")
    void verify_new_user_verified_before() { // 새롭게 인증 코드를 부여하고 정상 동작해야 함
        assertDoesNotThrow(() -> mailService.sendMail(VERIFIED_USER_EMAIL));
    }

    @Test
    @DisplayName("이메일 코드 발급 - 실패 - 인증 받았던 이력이 있고 이미 회원인 사용자")
    void verify_new_user_verified_before_and_service_user() { //
        UserExistsException exception = assertThrows(UserExistsException.class,
                () -> mailService.sendMail(VERIFIED_AND_SERVICE_USER_EMAIL));

        assertNotNull(exception.getMessage());
    }

    @Test
    @DisplayName("이메일 인증")
    void authenticate() {
        // 인증 코드를 받고 
    }

}