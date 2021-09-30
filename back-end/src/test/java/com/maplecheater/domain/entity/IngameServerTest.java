package com.maplecheater.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IngameServerTest {

    @Test
    @DisplayName("서버 생성 테스트")
    void create() {

        IngameServer server = new IngameServer(1L, "베라");

        assertEquals(1L, server.getId());
        assertEquals("베라", server.getServer());
    }

}