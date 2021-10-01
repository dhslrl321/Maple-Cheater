package com.maplecheater.domain.dto.response;

import com.maplecheater.domain.type.ReportStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportDetailResponseData {
    private Long reportId;
    private LocalDateTime registeredAt;
    private String cheaterNickname;
    private String cheaterServer;
    private String cheatingType;
    private LocalDateTime cheatingDatetime;
    private String situation;
    private ReportStatus status;
}
