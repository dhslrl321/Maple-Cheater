package com.maplecheater.exception;

public class VerificationNotFoundException extends RuntimeException {
    public VerificationNotFoundException() { super("이메일 인증 정보가 존재하지 않습니다."); }
}
