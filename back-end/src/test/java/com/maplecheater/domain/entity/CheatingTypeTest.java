package com.maplecheater.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheatingTypeTest {

    @Test
    @DisplayName("신고 타입 생성")
    void create() {
        CheatingType type = new CheatingType(1L, "현금 거래");
        assertNotNull(type);
    }

}