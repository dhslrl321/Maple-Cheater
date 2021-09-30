package com.maplecheater.domain.repository.cheaterdetail;

import com.maplecheater.domain.entity.CheaterDetail;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.maplecheater.domain.entity.QCheaterDetail.cheaterDetail;
import static com.maplecheater.domain.entity.QCheater.cheater;

public class CheaterDetailRepositoryImpl implements CheaterDetailRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public CheaterDetailRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<CheaterDetail> findAllByCheaterNickname(String ingameNickname) {
        return queryFactory
                .selectFrom(cheaterDetail)
                .join(cheaterDetail.cheater, cheater)
                .where(cheater.ingameNickname.eq(ingameNickname))
                .fetch();
    }
}
