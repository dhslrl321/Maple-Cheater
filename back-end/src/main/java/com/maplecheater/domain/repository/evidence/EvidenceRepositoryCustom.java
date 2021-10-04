package com.maplecheater.domain.repository.evidence;

import com.maplecheater.domain.dto.response.EvidenceImageResponseData;

import java.util.List;

public interface EvidenceRepositoryCustom {
    List<EvidenceImageResponseData> findAllByReportId(Long reportId);
}
