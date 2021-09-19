package com.maplecheater.domain.entity;

import com.maplecheater.domain.type.VerificationType;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
    private VerificationType verified;

    public void verify() {
        verified = VerificationType.VERIFIED;
    }
}
