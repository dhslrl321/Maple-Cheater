package com.maplecheater.exception;

public class AuthenticationFailedException extends RuntimeException {
    public AuthenticationFailedException() {
        super("아이디 혹은 비밀번호를 확인해보세요");
    }
}
