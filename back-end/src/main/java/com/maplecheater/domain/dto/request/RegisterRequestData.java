package com.maplecheater.domain.dto.request;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter @Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestData {
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
    @NotEmpty
    private String nickname;
}
