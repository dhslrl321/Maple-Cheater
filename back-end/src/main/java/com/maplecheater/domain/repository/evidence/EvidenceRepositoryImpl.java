package com.maplecheater.domain.repository.evidence;

import com.maplecheater.domain.dto.response.EvidenceImageResponseData;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.maplecheater.domain.entity.QReport.report;
import static com.maplecheater.domain.entity.QEvidence.evidence;

public class EvidenceRepositoryImpl implements EvidenceRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public EvidenceRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<EvidenceImageResponseData> findAllByReportId(Long reportId) {
        return queryFactory
                .select(Projections.fields(EvidenceImageResponseData.class,
                        evidence.imageUrl
                ))
                .from(evidence)
                .join(evidence.report, report)
                .where(report.id.eq(reportId))
                .fetch();
    }
}
