package com.jspbbs.web.exception;

public class UserExistsException extends Exception {
    public UserExistsException() {
        super("用户名已存在");
    }
}
