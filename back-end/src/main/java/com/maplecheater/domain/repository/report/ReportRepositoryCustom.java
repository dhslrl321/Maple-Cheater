package com.maplecheater.domain.repository.report;

import com.maplecheater.domain.dto.response.ReportDetailResponseData;
import com.maplecheater.domain.dto.response.ReportPreviewResponseData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ReportRepositoryCustom {
    Page<ReportPreviewResponseData> findAllDTO(Pageable pageable);

    Page<ReportPreviewResponseData> findAllByUserIdDTO(Pageable pageable, Long userId);

    Optional<ReportDetailResponseData> findByReportIdAndUserIdDTO(Long reportId, Long userId);

    Optional<ReportDetailResponseData> findByIdDTO(Long id);
}
