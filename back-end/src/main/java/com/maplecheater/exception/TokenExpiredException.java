package com.maplecheater.exception;

public class TokenExpiredException extends RuntimeException {
    public TokenExpiredException() {
        super("토큰의 기간이 만료되었습니다.");
    }
}
