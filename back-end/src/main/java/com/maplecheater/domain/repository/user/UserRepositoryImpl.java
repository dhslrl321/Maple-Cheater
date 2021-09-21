package com.maplecheater.domain.repository.user;

import com.maplecheater.domain.entity.QUser;
import com.maplecheater.domain.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.Optional;

import static com.maplecheater.domain.entity.QUser.user;

public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public UserRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(queryFactory
                .select(user)
                .from(user)
                .where(user.email.eq(email))
                .fetchOne());
    }

    @Override
    public boolean existsByEmail(String email) {
        Integer fetchOne = queryFactory
                .selectOne()
                .from(user)
                .where(user.email.eq(email))
                .fetchOne();

        return fetchOne != null;
    }

    @Override
    public boolean existsAndNotUnregisteredByEmail(String email) {
        Integer fetchOne = queryFactory
                .selectOne()
                .from(user)
                .where(user.email.eq(email),
                        user.unregisteredAt.isNull())
                .fetchOne();

        return fetchOne != null;
    }
}
