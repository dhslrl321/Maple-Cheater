package com.maplecheater.domain.dto.request;

import lombok.*;

@Getter @Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequestData {
    private String oldPassword;
    private String newPassword;
}
