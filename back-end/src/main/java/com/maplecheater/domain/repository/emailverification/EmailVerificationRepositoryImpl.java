package com.maplecheater.domain.repository.emailverification;

import com.maplecheater.domain.entity.EmailVerification;
import com.maplecheater.domain.type.VerificationType;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

import static com.maplecheater.domain.entity.QEmailVerification.emailVerification;

public class EmailVerificationRepositoryImpl implements EmailVerificationRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public EmailVerificationRepositoryImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public EmailVerification findByEmail(String email) {
        return queryFactory
                .selectFrom(emailVerification)
                .where(emailVerification.email.eq(email))
                .fetchOne();
    }

    @Override
    public VerificationType findVerifiedByEmail(String email) {
        return queryFactory
                .select(emailVerification.verified)
                .from(emailVerification)
                .where(emailVerification.email.eq(email))
                .fetchOne();
    }
}
