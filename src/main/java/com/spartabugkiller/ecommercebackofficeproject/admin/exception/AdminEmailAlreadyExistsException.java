package com.spartabugkiller.ecommercebackofficeproject.admin.exception;

import com.spartabugkiller.ecommercebackofficeproject.global.exception.ServiceException;
import org.springframework.http.HttpStatus;

public class AdminEmailAlreadyExistsException extends ServiceException {

    public AdminEmailAlreadyExistsException() {
        super(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다.");
    }
}
