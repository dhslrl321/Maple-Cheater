package com.maplecheater.domain.repository.emailverification;

import com.maplecheater.domain.entity.EmailVerification;
import com.maplecheater.domain.type.VerificationType;

public interface EmailVerificationRepositoryCustom {
    EmailVerification findByEmail(String email);

    VerificationType findVerifiedByEmail(String email);
}
