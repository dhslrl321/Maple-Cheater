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
    private LocalDateTime registeredAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "ingame_server_id")
    private IngameServer ingameServer;

    @ManyToOne
    @JoinColumn(name = "cheating_type_id")
    private CheatingType cheatingType;

    /**
     * 스스로 상태를 PENDING 에서 ACCEPTED 로 변경한다.
     */
    public void accept() {
        this.status = ReportStatus.ACCEPTED;
    }

    /**
     * 스스로 상태를 PENDING 에서 REJECTED 로 변경한다.
     */
    public void reject() {
        this.status = ReportStatus.REJECTED;
    }
}
