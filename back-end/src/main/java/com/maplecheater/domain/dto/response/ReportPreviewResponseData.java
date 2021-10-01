package com.maplecheater.domain.dto.response;

import com.maplecheater.domain.type.ReportStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportPreviewResponseData {
    private Long reportId;
    private String reporterNickname;
    private ReportStatus status;
    private String cheatingType;
    private LocalDateTime registeredAt;
}
