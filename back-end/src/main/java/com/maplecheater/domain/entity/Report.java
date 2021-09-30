package com.maplecheater.domain.entity;

import com.maplecheater.domain.type.ReportStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ingameNickname;
    private LocalDateTime cheatingDatetime;
    private String situation;
    @Enumerated(EnumType.STRING)
    private ReportStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingame_server_id")
    private IngameServer ingameServer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cheating_type_id")
    private CheatingType cheatingType;
}
