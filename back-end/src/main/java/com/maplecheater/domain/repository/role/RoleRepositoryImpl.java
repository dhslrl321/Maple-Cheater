package com.maplecheater.domain.repository.role;

import com.maplecheater.domain.entity.Role;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.maplecheater.domain.entity.QRole.role;

public class RoleRepositoryImpl implements RoleRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public RoleRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Role> findAllByUserId(Long userId) {
        return queryFactory
                .selectFrom(role)
                .where(role.userId.eq(userId))
                .fetch();
    }
}
