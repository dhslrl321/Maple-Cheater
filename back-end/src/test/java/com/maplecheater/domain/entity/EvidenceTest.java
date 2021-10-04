package com.maplecheater.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EvidenceTest {
    @Test
    @DisplayName("생성")
    void create() {
        Evidence evidence = new Evidence(1L, "url", new Report());

        assertNotNull(evidence);
    }
}
