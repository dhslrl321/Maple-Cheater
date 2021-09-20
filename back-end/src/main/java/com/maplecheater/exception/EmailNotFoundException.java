package com.maplecheater.exception;

public class EmailNotFoundException extends RuntimeException {
    public EmailNotFoundException() { super("존재하지 않는 이메일입니다."); }
}
