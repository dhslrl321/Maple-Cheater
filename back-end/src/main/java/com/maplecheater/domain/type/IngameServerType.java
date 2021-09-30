package com.maplecheater.domain.type;

import lombok.Getter;

@Getter
public enum IngameServerType {
    SCANIA(1L, "스카니아"),
    BERA(2L, "베라"),
    LUNA(3L, "루나"),
    ZENITH(4L, "제니스"),
    CROA(5L, "크로아"),
    UNION(6L, "유니온"),
    ELYSIUM(7L, "엘리시움"),
    INOSIS(8L, "이노시스"),
    RED(9L, "레드"),
    AURORA(10L, "오로라"),
    ARCANE(11L, "아케인"),
    NOVA(12L, "노바"),
    REBOOT(13L, "리부트"),
    REBOOT2(14L, "리부트2");

    private final Long id;
    private final String server;

    IngameServerType(Long id, String server) {
        this.id = id;
        this.server = server;
    }
}
