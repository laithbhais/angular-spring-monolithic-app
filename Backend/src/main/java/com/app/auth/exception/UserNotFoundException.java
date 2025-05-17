package com.app.auth.exception;


public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
        super("User not found");
    }
}
