package com.maplecheater.domain.dto.response;

import lombok.*;

@Getter @Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidateUserResponseData {
    private Long userId;
    private String email;
    private String nickname;
}
