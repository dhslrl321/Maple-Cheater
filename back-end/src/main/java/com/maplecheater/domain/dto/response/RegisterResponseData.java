package com.maplecheater.domain.dto.response;

import lombok.*;

@Getter @Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponseData {
    private String email;
    private String nickname;
}
