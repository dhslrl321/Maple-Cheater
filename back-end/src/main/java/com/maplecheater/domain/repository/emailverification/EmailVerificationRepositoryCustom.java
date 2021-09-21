package com.maplecheater.domain.repository.emailverification;

import com.maplecheater.domain.entity.EmailVerification;
import com.maplecheater.domain.type.VerificationType;

import java.util.Optional;

public interface EmailVerificationRepositoryCustom {
    Optional<EmailVerification> findByEmail(String email);

    Optional<VerificationType> findVerifiedByEmail(String email);

    boolean existsByEmail(String email);

}
