package com.maplecheater.domain.entity;

import com.maplecheater.domain.type.VerificationType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EmailVerificationTest {

    @Test
    @DisplayName("객체 생성 테스트")
    void create() {
        EmailVerification verification = EmailVerification.builder()
                .email("test@test.com")
                .code("q31n34")
                .verified(VerificationType.UNVERIFIED)
                .build();

        assertNotNull(verification);
    }

    @Test
    @DisplayName("확인 테스트")
    void verify() {
        EmailVerification verification = EmailVerification.builder()
                .email("test@test.com")
                .code("q31n34")
                .authDate(LocalDateTime.now())
                .verified(VerificationType.UNVERIFIED)
                .build();

        assertEquals(VerificationType.UNVERIFIED, verification.getVerified());

        verification.verify();

        assertEquals(VerificationType.VERIFIED, verification.getVerified());
    }

    @Test
    @DisplayName("인증 코드 갱신")
    void refreshCode() {
        String beforeCode = "q31n34";
        String newCode = "239771";

        EmailVerification verification = EmailVerification.builder()
                .email("test@test.com")
                .code(beforeCode)
                .verified(VerificationType.UNVERIFIED)
                .build();

        verification.refreshCode(newCode);

        assertNotEquals(beforeCode, verification.getCode());
    }

}