package com.maplecheater.domain.dto.request;

import lombok.*;

@Getter @Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestData {
    private String email;
    private String password;
    private String nickname;
}
