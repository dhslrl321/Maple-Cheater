package com.maplecheater.domain.dto.request;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddCheaterRequestData {
    private String ingameNickname;
    private Long ingameServer;
    private Long cheatingType;
    private String situation;
    private LocalDateTime cheatingDatetime;
}
