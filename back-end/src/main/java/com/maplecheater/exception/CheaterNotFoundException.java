package com.maplecheater.exception;

public class CheaterNotFoundException extends RuntimeException {
    public CheaterNotFoundException(String ingameNickname) {
        super(ingameNickname + " 으로 신고된 이력이 없습니다.");
    }
}
