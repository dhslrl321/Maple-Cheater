package com.maplecheater.domain.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheaterDetailResponseData {
    private Long cheaterId;
    private String cheaterNickname;
    private String cheatingType;
    private LocalDateTime cheatingDatetime;
    private String situation;
}
