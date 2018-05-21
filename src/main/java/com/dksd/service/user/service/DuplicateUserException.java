package com.dksd.service.user.service;

public class DuplicateUserException extends Exception {

    public DuplicateUserException(String msg) {
        super(msg);
    }
}
