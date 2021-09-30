package com.maplecheater.domain.type;

public enum CheatingType {
    CASH(1, "현금 거래"),
    SCROLL(2, "주문서 거래"),
    GRIEF(3, "사냥터 비매너");

    private final Integer id;
    private final String type;

    CheatingType(Integer id, String type) {
        this.id = id;
        this.type = type;
    }
}
