package com.maplecheater.exception;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException() {
        super("서버에서 생성한 토큰이 아닙니다.");
    }
}
