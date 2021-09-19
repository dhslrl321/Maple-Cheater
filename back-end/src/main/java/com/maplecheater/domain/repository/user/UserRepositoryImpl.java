package com.maplecheater.domain.repository.user;

import com.maplecheater.domain.entity.QUser;
import com.maplecheater.domain.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public UserRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public User findByEmail(String email) {
        return queryFactory
                .select(QUser.user)
                .from(QUser.user)
                .where(QUser.user.email.eq(email))
                .fetchOne();
    }
}
