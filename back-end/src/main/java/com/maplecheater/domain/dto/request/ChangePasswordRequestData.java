package com.maplecheater.domain.dto.request;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter @Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequestData {
    private String oldPassword;
    private String newPassword;
}
