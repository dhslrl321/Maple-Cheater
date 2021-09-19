package com.maplecheater.domain.entity;

import com.maplecheater.domain.type.VerificationType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
                .verified(VerificationType.UNVERIFIED)
                .build();

        assertEquals(VerificationType.UNVERIFIED, verification.getVerified());

        verification.verify();

        assertEquals(VerificationType.VERIFIED, verification.getVerified());
    }

}