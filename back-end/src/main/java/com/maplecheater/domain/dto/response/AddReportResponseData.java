package com.maplecheater.domain.dto.response;

import lombok.*;

@Getter @Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddReportResponseData {
    private String ingameNickname;
    private String ingameServer;
    private String cheatingType;
}
