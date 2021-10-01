package com.maplecheater.domain.repository.report;

import com.maplecheater.domain.dto.response.ReportDetailResponseData;
import com.maplecheater.domain.dto.response.ReportPreviewResponseData;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.maplecheater.domain.entity.QReport.report;
import static com.maplecheater.domain.entity.QUser.user;

public class ReportRepositoryImpl implements ReportRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public ReportRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<ReportPreviewResponseData> findAllDTO(Pageable pageable) {

        List<ReportPreviewResponseData> results = queryFactory
                .select(getPreviewData())
                .from(report)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPQLQuery<ReportPreviewResponseData> count = queryFactory
                .select(getPreviewData())
                .from(report);

        return PageableExecutionUtils.getPage(results, pageable, () -> count.fetchCount());
    }

    @Override
    public Page<ReportPreviewResponseData> findAllByUserIdDTO(Pageable pageable, Long userId) {

        List<ReportPreviewResponseData> results = queryFactory
                .select(getPreviewData())
                .from(report)
                .join(report.user, user)
                .where(user.id.eq(userId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPQLQuery<ReportPreviewResponseData> count = queryFactory
                .select(getPreviewData())
                .from(report)
                .join(report.user, user)
                .where(user.id.eq(userId));

        return PageableExecutionUtils.getPage(results, pageable, () -> count.fetchCount());
    }

    @Override
    public Optional<ReportDetailResponseData> findByReportIdAndUserIdDTO(Long reportId, Long userId) {
        return Optional.ofNullable(
                queryFactory
                .select(getDetailData())
                .from(report)
                .join(report.user, user)
                .where(
                        user.id.eq(userId)
                        .and(report.id.eq(reportId))
                ).fetchOne());
    }

    @Override
    public Optional<ReportDetailResponseData> findByIdDTO(Long id) {
        return Optional.ofNullable(
                queryFactory
                .select(getDetailData())
                .from(report)
                .where(report.id.eq(id))
                .fetchOne());
    }

    private static QBean<ReportPreviewResponseData> getPreviewData() {
        return Projections.fields(ReportPreviewResponseData.class,
                report.id.as("reportId"),
                report.user.nickname.as("reporterNickname"),
                report.status,
                report.cheatingType.type.as("cheatingType"),
                report.registeredAt);
    }

    private static QBean<ReportDetailResponseData> getDetailData() {
        return Projections.fields(ReportDetailResponseData.class,
                report.id.as("reportId"),
                report.registeredAt,
                report.ingameNickname.as("cheaterNickname"),
                report.ingameServer.server.as("cheaterServer"),
                report.cheatingType.type.as("cheatingType"),
                report.cheatingDatetime,
                report.situation,
                report.status
                );
    }
}
