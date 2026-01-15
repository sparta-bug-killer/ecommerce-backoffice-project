package com.spartabugkiller.ecommercebackofficeproject.admin.exception;

import com.spartabugkiller.ecommercebackofficeproject.global.exception.ServiceException;
import org.springframework.http.HttpStatus;

public class AdminBlockedException extends ServiceException {

    public AdminBlockedException() {
        super(HttpStatus.LOCKED, "정지된 계정입니다.");
    }
}
