package com.maplecheater.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String nickname;
    private LocalDateTime registeredAt;
    private LocalDateTime unregisteredAt;

    /**
     * 회원가입 시에 호출이 되는데, 현재 시간을 기준으로 registeredAt 의 시간을 채운다.
     */
    public void register() {
        this.registeredAt = LocalDateTime.now();
    }
}
