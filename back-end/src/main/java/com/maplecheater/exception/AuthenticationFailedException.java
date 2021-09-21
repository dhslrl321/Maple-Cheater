package com.maplecheater.exception;

public class AuthenticationFailedException extends RuntimeException {
    public AuthenticationFailedException() {
        super("인증 정보가 일치하지 않습니다.");
    }
}
