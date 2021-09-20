package com.maplecheater.domain.entity;

import com.maplecheater.domain.type.RoleType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    @Test
    @DisplayName("생성 테스트")
    void create_with_builder() {
        Role role = Role.builder()
                .id(1L)
                .userId(1L)
                .roleType(RoleType.USER)
                .build();

        assertEquals(RoleType.USER, role.getRoleType());
    }

    @Test
    @DisplayName("생성 테스트")
    void create_with_constructor() {
        Role role = new Role(1L, RoleType.USER);

        assertEquals(RoleType.USER, role.getRoleType());
    }

}