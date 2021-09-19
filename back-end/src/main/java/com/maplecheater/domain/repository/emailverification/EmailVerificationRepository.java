package com.maplecheater.domain.repository.emailverification;

import com.maplecheater.domain.entity.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailVerificationRepository extends JpaRepository<EmailVerification, Long>, EmailVerificationRepositoryCustom {

}
