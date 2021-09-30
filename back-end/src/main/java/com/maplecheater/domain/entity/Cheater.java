package com.maplecheater.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cheater {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ingameNickname;
    private LocalDateTime registeredAt;

    @ManyToOne
    @JoinColumn(name = "ingame_server_id")
    private IngameServer ingameServer;
}
