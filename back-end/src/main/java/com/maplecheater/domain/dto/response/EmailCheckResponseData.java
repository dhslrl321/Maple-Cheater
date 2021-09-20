package com.maplecheater.domain.dto.response;

import lombok.*;

@Getter @Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailCheckResponseData {
    private Boolean isExist;
}
