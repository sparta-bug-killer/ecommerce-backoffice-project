package com.spartabugkiller.ecommercebackofficeproject.admin.exception;

import com.spartabugkiller.ecommercebackofficeproject.global.exception.ServiceException;
import org.springframework.http.HttpStatus;

public class AdminPendingException extends ServiceException {

    public AdminPendingException() {
        super(HttpStatus.FORBIDDEN, "승인 대기 중인 계정입니다.");
    }
}
