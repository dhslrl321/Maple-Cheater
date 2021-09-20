package com.maplecheater.service;

import com.maplecheater.domain.dto.request.ChangePasswordRequestData;
import com.maplecheater.domain.dto.request.RegisterRequestData;
import com.maplecheater.domain.dto.response.EmailCheckResponseData;
import com.maplecheater.domain.dto.response.RegisterResponseData;
import com.maplecheater.domain.entity.EmailVerification;
import com.maplecheater.domain.entity.User;
import com.maplecheater.domain.repository.emailverification.EmailVerificationRepository;
import com.maplecheater.domain.repository.role.RoleRepository;
import com.maplecheater.domain.repository.user.UserRepository;
import com.maplecheater.domain.type.VerificationType;
import com.maplecheater.exception.AuthenticationFailedException;
import com.maplecheater.exception.EmailNotFoundException;
import com.maplecheater.exception.InvalidVerificationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

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

    private static final String NEW_PASSWORD = "passWord";

    private UserRepository userRepository = mock(UserRepository.class);
    private EmailVerificationRepository emailVerificationRepository = mock(EmailVerificationRepository.class);
    private RoleRepository roleRepository = mock(RoleRepository.class);

    private UserService userService;

    @BeforeEach
    void setUp() {
        ModelMapper modelMapper = new ModelMapper();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userService = new UserService(
                userRepository,
                emailVerificationRepository,
                roleRepository,
                modelMapper,
                passwordEncoder);

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

        given(userRepository.findByEmail(EMAIL)).willReturn(Optional.of(user));
        given(userRepository.findByEmail(EMAIL_NOT_VERIFIED)).willReturn(Optional.of(user));

        given(emailVerificationRepository.findByEmail(EMAIL))
                .willReturn(emailVerification);
        given(emailVerificationRepository.findByEmail(EMAIL_NOT_VERIFIED))
                .willReturn(emailVerification);

        given(emailVerificationRepository.findVerifiedByEmail(EMAIL))
                .willReturn(VerificationType.VERIFIED);
        given(emailVerificationRepository.findVerifiedByEmail(EMAIL_NOT_VERIFIED))
                .willReturn(VerificationType.UNVERIFIED);

        given(userRepository.findById(1L)).willReturn(Optional.of(user));

        given(userRepository.existsByEmail(EMAIL))
                .willReturn(true);

    }

    @Test
    @DisplayName("중복된 이메일이 존재하는지 확인하는 테스트")
    void isExistEmail() {
        EmailCheckResponseData existEmail = userService.isExistEmail(EMAIL);
        assertEquals(true, existEmail.getIsExist());
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

    @Test
    @DisplayName("비밀번호 변경 - 성공")
    void changePassword_success() {
        ChangePasswordRequestData request = ChangePasswordRequestData.builder()
                .oldPassword(PASSWORD)
                .newPassword(NEW_PASSWORD)
                .build();

        userService.changePassword(1L, request, 1L);
    }

    @Test
    @DisplayName("비밀번호 변경 - 실패 - targetId 와 tokenId 가 다를 경우")
    void changePassword_fail_different_id() {
        ChangePasswordRequestData request = ChangePasswordRequestData.builder()
                .oldPassword(PASSWORD)
                .newPassword(NEW_PASSWORD)
                .build();

        AuthenticationFailedException exception = assertThrows(AuthenticationFailedException.class,
                () -> userService.changePassword(1L, request, 2L));

        assertNotNull(exception);
    }

    @Test
    @DisplayName("비밀번호 변경 - 실패 기존 비밀번호 인증 불가")
    void changePassword_fail_different_password() {
        ChangePasswordRequestData request = ChangePasswordRequestData.builder()
                .oldPassword("different_password")
                .newPassword(NEW_PASSWORD)
                .build();

        AuthenticationFailedException exception = assertThrows(AuthenticationFailedException.class,
                () -> userService.changePassword(1L, request, 1L));

        assertNotNull(exception);
    }
}