package com.maplecheater.domain.repository.report;

import com.maplecheater.domain.entity.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ReportRepositoryCustom {
    Page<Report> findAllByUserId(Pageable pageable, Long userId);

    Optional<Report> findByReportIdAndUserId(Long reportId, Long userId);
}
