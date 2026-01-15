package com.spartabugkiller.ecommercebackofficeproject.admin.exception;

import com.spartabugkiller.ecommercebackofficeproject.global.exception.ServiceException;
import org.springframework.http.HttpStatus;

public class AdminInvalidEmailException extends ServiceException {

    public AdminInvalidEmailException() {
        super(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 일치하지 않습니다.");
    }
}
