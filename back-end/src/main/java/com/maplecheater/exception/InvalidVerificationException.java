package com.maplecheater.exception;

public class InvalidVerificationException extends RuntimeException {
    public InvalidVerificationException() {
        super("이메일 인증 정보가 올바르지 않습니다.");
    }
}
