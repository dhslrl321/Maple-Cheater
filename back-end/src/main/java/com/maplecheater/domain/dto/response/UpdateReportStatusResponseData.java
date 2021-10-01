package com.maplecheater.domain.dto.response;

import com.maplecheater.domain.type.ReportStatus;
import lombok.*;

import javax.persistence.SecondaryTable;

@Getter @Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateReportStatusResponseData {
    private Long reportId;
    private String reporterEmail;
    private String reporterNickname;
    private String cheaterIngameNickname;
    private ReportStatus status;
}
