package com.maplecheater.domain.repository.cheaterdetail;

import com.maplecheater.domain.dto.response.CheaterDetailResponseData;
import com.maplecheater.domain.entity.CheaterDetail;
import com.querydsl.core.types.Projections;
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
    public List<CheaterDetailResponseData> findAllByCheaterNickname(String ingameNickname) {
        return queryFactory
                .select(Projections.fields(CheaterDetailResponseData.class,
                        cheater.id.as("cheaterId"),
                        cheaterDetail.cheater.ingameNickname.as("cheaterNickname"),
                        cheaterDetail.cheatingType.type.as("cheatingType"),
                        cheaterDetail.cheatingDatetime.as("cheatingDatetime"),
                        cheaterDetail.situation
                        ))
                .from(cheaterDetail)
                .join(cheaterDetail.cheater, cheater)
                .where(cheater.ingameNickname.eq(ingameNickname))
                .fetch();
    }
}
