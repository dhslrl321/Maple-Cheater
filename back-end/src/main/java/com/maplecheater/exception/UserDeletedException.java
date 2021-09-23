package com.maplecheater.exception;

public class UserDeletedException extends RuntimeException {
    public UserDeletedException() {
        super("이미 삭제된 사용자입니다.");
    }
}
