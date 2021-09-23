package com.maplecheater.service;

import com.maplecheater.domain.entity.EmailVerification;
import com.maplecheater.domain.entity.User;
import com.maplecheater.domain.repository.emailverification.EmailVerificationRepository;
import com.maplecheater.domain.repository.user.UserRepository;
import com.maplecheater.domain.type.VerificationType;
import com.maplecheater.exception.AuthenticationFailedException;
import com.maplecheater.exception.UserExistsException;
import com.maplecheater.exception.UserNotFoundException;
import com.maplecheater.util.MailUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

class MailServiceTest {
    private static final String NEW_USER_EMAIL = "new@new.com";
    private static final String VERIFIED_USER_EMAIL = "verified@verified.com";
    private static final String VERIFIED_AND_SERVICE_USER_EMAIL = "service@service.com";

    private static final String AUTH_SUCCESS_USER_EMAIL = "success@com.com";
    private static final String AUTH_FAIL_USER_EMAIL = "auth@fail.com";
    private static final String NOT_EXIST_USER_EMAIL = "no@noex.com";

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
                mailUtil,
                new BCryptPasswordEncoder());

        User serviceUser = User.builder()
                .id(1L)
                .email(VERIFIED_AND_SERVICE_USER_EMAIL)
                .password("password")
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

        EmailVerification verification = EmailVerification.builder()
                .email(AUTH_SUCCESS_USER_EMAIL)
                .code("123456")
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

        given(emailVerificationRepository.findByEmail(AUTH_SUCCESS_USER_EMAIL))
                .willReturn(Optional.of(verification));

        given(emailVerificationRepository.findByEmail(AUTH_FAIL_USER_EMAIL))
                .willReturn(Optional.of(verification));

        given(emailVerificationRepository.findByEmail(NOT_EXIST_USER_EMAIL))
                .willReturn(Optional.empty());

        given(userRepository.findByEmail(AUTH_SUCCESS_USER_EMAIL))
                .willReturn(Optional.of(serviceUser));

        given(userRepository.findByEmail(NOT_EXIST_USER_EMAIL))
                .willReturn(Optional.empty());

        given(mailUtil.generateTempPassword())
                .willReturn("ad14f97ada");
    }

    @Test
    @DisplayName("이메일 코드 발급 - 새로운 사용자")
    void verify_new_user() {
        assertDoesNotThrow(() -> mailService.sendAuthMail(NEW_USER_EMAIL));
    }

    @Test
    @DisplayName("이메일 코드 발급 - 인증 받았던 이력이 있는 사용자")
    void verify_new_user_verified_before() { // 새롭게 인증 코드를 부여하고 정상 동작해야 함
        assertDoesNotThrow(() -> mailService.sendAuthMail(VERIFIED_USER_EMAIL));
    }

    @Test
    @DisplayName("이메일 코드 발급 - 실패 - 인증 받았던 이력이 있고 이미 회원인 사용자")
    void verify_new_user_verified_before_and_service_user() { //
        UserExistsException exception = assertThrows(UserExistsException.class,
                () -> mailService.sendAuthMail(VERIFIED_AND_SERVICE_USER_EMAIL));

        assertNotNull(exception.getMessage());
    }

    @Test
    @DisplayName("이메일 인증 번호 비교 - 같은 코드")
    void authenticate_success() {
        String code = "123456";

        assertDoesNotThrow(() -> mailService.authenticate(AUTH_SUCCESS_USER_EMAIL, code));
    }

    @Test
    @DisplayName("이메일 인증 번호 비교 - 존재하지 않는 사용자")
    void authenticate_fail_not_exists_user() {
        String code = "123456";

        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> mailService.authenticate(NOT_EXIST_USER_EMAIL, code));

        assertNotNull(exception.getMessage());
    }

    @Test
    @DisplayName("이메일 인증 번호 비교 - 다른 코드")
    void authenticate_fail_different_code() {
        String code = "456123";

        AuthenticationFailedException exception = assertThrows(AuthenticationFailedException.class,
                () -> mailService.authenticate(AUTH_FAIL_USER_EMAIL, code));

        assertNotNull(exception.getMessage());
    }

    @Test
    @DisplayName("임시 비밀번호 발급 - 성공")
    void tempPassword_success() {
        assertDoesNotThrow(() -> mailService.sendTempPasswordMail(AUTH_SUCCESS_USER_EMAIL));
    }

    @Test
    @DisplayName("임시 비밀번호 발급 - 실패")
    void tempPassword_fail_user_not_found() {
        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> mailService.sendTempPasswordMail(NOT_EXIST_USER_EMAIL));

        assertNotNull(exception.getMessage());
    }

}