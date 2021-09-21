package com.maplecheater.domain.entity;

import com.maplecheater.domain.type.VerificationType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String code;
    private LocalDateTime authDate;

    @Enumerated(EnumType.STRING)
    private VerificationType verified;

    public void verify() {
        verified = VerificationType.VERIFIED;
    }

    public void refreshCode(String newCode) {
        this.authDate = LocalDateTime.now();
        this.code = newCode;
    }
}
