package com.spartabugkiller.ecommercebackofficeproject.global.exception;

public class AdminEmailAlreadyExistsException extends RuntimeException {
    public AdminEmailAlreadyExistsException() {
        super("이미 사용 중인 이메일입니다.");
    }
}
