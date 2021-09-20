package com.maplecheater.domain.entity;

import com.maplecheater.domain.type.RoleType;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    public Role(Long userId, RoleType roleType) {
        this.userId = userId;
        this.roleType = roleType;
    }
}
