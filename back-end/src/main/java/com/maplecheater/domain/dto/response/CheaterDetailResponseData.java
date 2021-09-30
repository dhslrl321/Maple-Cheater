package com.maplecheater.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheaterDetailResponseData {
    private String situation;
    private LocalDateTime cheatingDatetime;
    private String cheatingType;
}
