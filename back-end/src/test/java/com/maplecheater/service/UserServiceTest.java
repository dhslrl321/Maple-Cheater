package com.maplecheater.service;

import com.maplecheater.domain.dto.request.ChangeNicknameRequestData;
import com.maplecheater.domain.dto.request.ChangePasswordRequestData;
import com.maplecheater.domain.dto.request.RegisterRequestData;
import com.maplecheater.domain.dto.response.EmailCheckResponseData;
import com.maplecheater.domain.dto.response.RegisterResponseData;
import com.maplecheater.domain.dto.response.ReportDetailResponseData;
import com.maplecheater.domain.dto.response.ReportPreviewResponseData;
import com.maplecheater.domain.entity.EmailVerification;
import com.maplecheater.domain.entity.User;
import com.maplecheater.domain.repository.emailverification.EmailVerificationRepository;
import com.maplecheater.domain.repository.report.ReportRepository;
import com.maplecheater.domain.repository.role.RoleRepository;
import com.maplecheater.domain.repository.user.UserRepository;
import com.maplecheater.domain.type.VerificationType;
import com.maplecheater.exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.mock;

class UserServiceTest {


    private static final Long VALID_USER_ID = 1L;
    private static final Long VALID_REPORT_ID = 1L;
    private static final Long INVALID_REPORT_ID = 22L;
    private static final Long INVALID_USER_ID = 2L;
    private static final Long NOT_EXIST_USER_ID = 20L;
    private static final Long NOT_EXIST_USER = 102L;

    private static final String EMAIL = "test@test.com";
    private static final String EXIST_USER_EMAIL = "exists@t.c";
    private static final String EMAIL_DOES_NOT_EXIST = "no@no.no";
    private static final String EMAIL_NOT_VERIFIED = "verify@verify.no";

    private static final Integer PAGE_INDEX = 0;
    private static final Integer PAGE_SIZE = 5;

    private static final String PASSWORD = "password";
    private static final String NICKNAME = "nickname";

    private static final String NEW_PASSWORD = "passWord";

    private static final String ENCODED_PASSWORD = "$2a$10$zSnzZDu5Jpyqch0zez9soekcecOTmgT8MFFzG.Sd7vClwexE.syd2";

    private UserRepository userRepository = mock(UserRepository.class);
    private EmailVerificationRepository emailVerificationRepository = mock(EmailVerificationRepository.class);
    private RoleRepository roleRepository = mock(RoleRepository.class);
    private ReportRepository reportRepository = mock(ReportRepository.class);

    private UserService userService;

    @BeforeEach
    void setUp() {
        ModelMapper modelMapper = new ModelMapper();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userService = new UserService(
                userRepository,
                emailVerificationRepository,
                roleRepository,
                reportRepository,
                modelMapper,
                passwordEncoder);

        User user = User.builder()
                .id(1L)
                .email(EMAIL)
                .password(PASSWORD)
                .nickname(NICKNAME)
                .registeredAt(LocalDateTime.now())
                .build();

        User userWithEncodedPassword = User.builder()
                .id(1L)
                .email(EMAIL)
                .password(ENCODED_PASSWORD)
                .nickname(NICKNAME)
                .registeredAt(LocalDateTime.now())
                .build();

        EmailVerification emailVerification = EmailVerification.builder()
                .id(1L)
                .email(EMAIL)
                .code("1A2B3C")
                .verified(VerificationType.UNVERIFIED)
                .build();

        given(userRepository.save(any(User.class)))
                .willReturn(user);

        given(userRepository.existsByEmail(EMAIL))
                .willReturn(false);

        given(userRepository.existsByEmail(EXIST_USER_EMAIL))
                .willReturn(true);

        given(userRepository.findByEmail(EMAIL))
                .willReturn(Optional.of(user));

        given(userRepository.findByEmail(EMAIL_NOT_VERIFIED))
                .willReturn(Optional.empty());

        given(emailVerificationRepository.findByEmail(EMAIL))
                .willReturn(Optional.of(emailVerification));
        given(emailVerificationRepository.findByEmail(EMAIL_NOT_VERIFIED))
                .willReturn(Optional.of(emailVerification));

        given(emailVerificationRepository.findVerifiedByEmail(EMAIL))
                .willReturn(Optional.of(VerificationType.VERIFIED));

        given(emailVerificationRepository.findVerifiedByEmail(EXIST_USER_EMAIL))
                .willReturn(Optional.of(VerificationType.VERIFIED));

        given(emailVerificationRepository.findVerifiedByEmail(EMAIL_NOT_VERIFIED))
                .willReturn(Optional.of(VerificationType.UNVERIFIED));

        given(userRepository.findById(1L))
                .willReturn(Optional.of(userWithEncodedPassword));

        given(userRepository.findById(20L))
                .willReturn(Optional.empty());

        given(userRepository.existsByEmail(EMAIL))
                .willReturn(false);

        List<ReportPreviewResponseData> reports = new ArrayList<>();

        IntStream.range(0, 10).forEach(each -> {
            reports.add(new ReportPreviewResponseData());
        });

        Page<ReportPreviewResponseData> pagedReports = new PageImpl<>(reports);

        given(reportRepository.findAllByUserIdDTO(PageRequest.of(PAGE_INDEX, PAGE_SIZE), VALID_USER_ID))
                .willReturn(pagedReports);

        given(reportRepository.findByReportIdAndUserIdDTO(VALID_REPORT_ID, VALID_USER_ID))
                .willReturn(Optional.of(new ReportDetailResponseData()));

        given(reportRepository.findByReportIdAndUserIdDTO(INVALID_REPORT_ID, VALID_USER_ID))
                .willReturn(Optional.empty());
    }

    @Test
    @DisplayName("????????? ???????????? ??????????????? ???????????? ?????????")
    void isExistEmail() {
        EmailCheckResponseData existEmail = userService.isExistEmail(EMAIL);
        assertEquals(false, existEmail.getIsExist());
    }

    @Test
    @DisplayName("????????? ????????? ???????????? ????????? - ??????")
    void checkVerifiedEmail_success() {
        boolean isVerified = userService.checkVerifiedEmail(EMAIL);

        assertTrue(isVerified);
    }

    @Test
    @DisplayName("????????? ????????? ???????????? ????????? - ?????? - ???????????? ?????? ?????????")
    void checkVerifiedEmail_fail_doesnt_exist_email() {
        VerificationNotFoundException verificationNotFoundException = assertThrows(VerificationNotFoundException.class, () ->
                userService.checkVerifiedEmail(EMAIL_DOES_NOT_EXIST));

        assertNotNull(verificationNotFoundException.getMessage());
    }

    @Test
    @DisplayName("?????? ?????? - ??????")
    void registerUser_success() {
        RegisterRequestData request = RegisterRequestData.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .nickname(NICKNAME)
                .build();

        RegisterResponseData response = userService.registerUser(request);

        assertEquals(EMAIL, response.getEmail());
        assertEquals(NICKNAME, response.getNickname());
    }

    @Test
    @DisplayName("?????? ?????? - ?????? - ?????? ???????????? ?????????")
    void registerUser_fail_exists_user() {
        RegisterRequestData request = RegisterRequestData.builder()
                .email(EXIST_USER_EMAIL)
                .password(PASSWORD)
                .nickname(NICKNAME)
                .build();

        UserExistsException exception = assertThrows(UserExistsException.class,
                () -> userService.registerUser(request));

        assertNotNull(exception.getMessage());
    }

    @Test
    @DisplayName("?????? ?????? - ?????? - ???????????? ?????? ?????????")
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
    @DisplayName("???????????? ?????? - ??????")
    void changePassword_success() {
        ChangePasswordRequestData request = ChangePasswordRequestData.builder()
                .oldPassword(PASSWORD)
                .newPassword(NEW_PASSWORD)
                .build();

        assertDoesNotThrow(() -> userService.changePassword(1L, request, 1L));
    }

    @Test
    @DisplayName("???????????? ?????? - ?????? - targetId ??? tokenId ??? ?????? ??????")
    void changePassword_fail_different_id() {
        ChangePasswordRequestData request = ChangePasswordRequestData.builder()
                .oldPassword(PASSWORD)
                .newPassword(NEW_PASSWORD)
                .build();

        UnauthorizedException exception = assertThrows(UnauthorizedException.class,
                () -> userService.changePassword(1L, request, 2L));

        assertNotNull(exception);
    }

    @Test
    @DisplayName("???????????? ?????? - ?????? ?????? ???????????? ?????? ??????")
    void changePassword_fail_different_password() {
        ChangePasswordRequestData request = ChangePasswordRequestData.builder()
                .oldPassword("different_password")
                .newPassword(NEW_PASSWORD)
                .build();

        AuthenticationFailedException exception = assertThrows(AuthenticationFailedException.class,
                () -> userService.changePassword(1L, request, 1L));

        assertNotNull(exception);
    }

    @Test
    @DisplayName("???????????? ?????? - ?????? - ???????????? ?????? ?????????")
    void changePassword_fail_user_not_found() {
        ChangePasswordRequestData request = ChangePasswordRequestData.builder()
                .oldPassword("different_password")
                .newPassword(NEW_PASSWORD)
                .build();

        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> userService.changePassword(NOT_EXIST_USER, request, NOT_EXIST_USER));

        assertNotNull(exception.getMessage());
    }

    @Test
    @DisplayName("????????? ?????? ????????? - ??????")
    void changeNickname_success() {
        ChangeNicknameRequestData request = new ChangeNicknameRequestData("newNickname");

        assertDoesNotThrow(() -> userService.changeNickname(1L, request, 1L));
    }

    @Test
    @DisplayName("????????? ?????? ????????? - ?????? - ???????????? ?????? ?????????")
    void changeNickname_fail() {
        ChangeNicknameRequestData request = new ChangeNicknameRequestData("newNickname");
        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> userService.changeNickname(102L, request, NOT_EXIST_USER));

        assertNotNull(exception);
    }

    @Test
    @DisplayName("?????? ??????")
    void unregister() {
        Long targetId = 1L;
        Long tokenUserId = 1L;

        assertDoesNotThrow(() -> userService.unregister(VALID_USER_ID, VALID_USER_ID));
    }

    @Test
    @DisplayName("?????? ?????? - ?????? - targetId ??? tokenUserId ??? ??????")
    void unregister_fail_different_id() {

        UnauthorizedException exception = assertThrows(UnauthorizedException.class,
                () -> userService.unregister(VALID_USER_ID, INVALID_USER_ID));

        assertNotNull(exception.getMessage());
    }

    @Test
    @DisplayName("?????? ?????? - ?????? - User ??? ???????????? ??????")
    void unregister_fail_not_exists_user() {
        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> userService.unregister(NOT_EXIST_USER_ID, NOT_EXIST_USER_ID));

        assertNotNull(exception.getMessage());
    }

    @Test
    @DisplayName("user id ??? ???????????? ?????? report ????????????")
    void getAllReports() {
        PageRequest pageRequest = PageRequest.of(0, 5);
        Long tokenUserId = 1L;

        Page<ReportPreviewResponseData> page = userService.getAllReports(pageRequest, 1L, tokenUserId);

        assertEquals(10, page.getTotalElements());
    }

    @Test
    @DisplayName("userId ??? reportId ??? ????????? ?????? ????????? ?????? - ??????")
    void getReport() {
        ReportDetailResponseData report = userService.getReport(VALID_REPORT_ID, 1L, VALID_USER_ID);
        assertNotNull(report);
    }

    @Test
    @DisplayName("userId ??? reportId ??? ????????? ?????? ????????? ?????? - ??????")
    void getReport_fail() {
        IllegalDataException exception = assertThrows(IllegalDataException.class,
                () -> userService.getReport(INVALID_REPORT_ID, 1L, VALID_USER_ID));

        assertNotNull(exception.getMessage());
    }
}