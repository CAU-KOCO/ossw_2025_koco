package com.back.introduction.Exception;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException() {
        super("该用户已存在");
    }

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
