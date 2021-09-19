package com.maplecheater.domain.repository;

import com.maplecheater.domain.entity.EmailVerification;
import com.maplecheater.domain.repository.emailverification.EmailVerificationRepository;
import com.maplecheater.domain.type.VerificationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
class EmailVerificationRepositoryTest {

    private static final String EMAIL = "test@test.com";

    @Autowired
    private EmailVerificationRepository emailVerificationRepository;

    @BeforeEach
    void setUp() {
        emailVerificationRepository.save(EmailVerification.builder()
                .email(EMAIL)
                .code("Q12S23")
                .verified(VerificationType.UNVERIFIED)
                .build());
    }

    @Test
    @DisplayName("findByEmail 확인 테스트")
    void findByEmail() {
        EmailVerification verification = emailVerificationRepository.findByEmail(EMAIL);

        assertEquals(EMAIL, verification.getEmail());
    }

    @Test
    @DisplayName("findVerifiedByEmail 확인 테스트")
    void findVerifiedByEmail() {
        VerificationType unverified = emailVerificationRepository.findVerifiedByEmail(EMAIL);

        assertEquals(VerificationType.UNVERIFIED, unverified);
    }

}