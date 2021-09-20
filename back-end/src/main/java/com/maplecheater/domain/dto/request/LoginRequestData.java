package com.maplecheater.domain.dto.request;

import lombok.*;

@Getter @Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestData {
    private String email;
    private String password;
}
