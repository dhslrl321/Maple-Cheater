package com.maplecheater.domain.dto.request;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter @Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddReportRequestData {
    private String ingameNickname;
    private LocalDateTime cheatingDatetime;
    private String situation;
    private Long userId;
    private Long ingameServer;
    private Long cheatingType;
}
