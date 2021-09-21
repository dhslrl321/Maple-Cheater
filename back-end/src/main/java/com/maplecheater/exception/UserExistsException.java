package com.maplecheater.exception;

public class UserExistsException extends RuntimeException {
    public UserExistsException() {
        super("이미 존재하는 사용자입니다.");
    }
}
