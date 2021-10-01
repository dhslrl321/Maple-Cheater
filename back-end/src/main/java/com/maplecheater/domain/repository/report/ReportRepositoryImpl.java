package com.maplecheater.domain.repository.report;

import com.maplecheater.domain.entity.Report;
import com.querydsl.core.QueryResults;
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
    public Page<Report> findAllByUserId(Pageable pageable, Long userId) {

        List<Report> results = queryFactory
                .selectFrom(report)
                .join(report.user, user)
                .where(user.id.eq(userId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPQLQuery<Report> count = queryFactory
                .selectFrom(report)
                .join(report.user, user)
                .where(user.id.eq(userId));

        return PageableExecutionUtils.getPage(results, pageable, () -> count.fetchCount());
    }

    @Override
    public Optional<Report> findByReportIdAndUserId(Long reportId, Long userId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(report)
                .join(report.user, user)
                .where(
                        user.id.eq(userId)
                                .and(report.id.eq(reportId))
                ).fetchOne());
    }
}
