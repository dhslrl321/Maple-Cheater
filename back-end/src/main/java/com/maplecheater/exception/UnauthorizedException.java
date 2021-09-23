package com.maplecheater.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super("올바른 접근이 아닙니다.");
    }
}
