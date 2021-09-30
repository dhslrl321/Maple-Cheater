package com.maplecheater.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheaterDetail {
    @Id
    @GeneratedValue
    private Long id;
    private String situation;
    private LocalDateTime cheatingDatetime;

    @ManyToOne
    @JoinColumn(name = "cheater_id")
    private Cheater cheater;

    @ManyToOne
    @JoinColumn(name = "cheating_type_id")
    private CheatingType cheatingType;
}
