package com.maplecheater.domain.repository.cheater;

import com.maplecheater.domain.entity.Cheater;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityManager;
import java.util.Optional;

import static com.maplecheater.domain.entity.QCheater.cheater;

public class CheaterRepositoryImpl implements CheaterRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public CheaterRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<Cheater> findByIngameNickname(String nickname) {
        return Optional.ofNullable(queryFactory
                .selectFrom(cheater)
                .where(cheater.ingameNickname.eq(nickname))
                .fetchOne());
    }
}
