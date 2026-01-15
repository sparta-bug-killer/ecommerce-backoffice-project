package com.spartabugkiller.ecommercebackofficeproject.admin.exception;

import com.spartabugkiller.ecommercebackofficeproject.global.exception.ServiceException;
import org.springframework.http.HttpStatus;

public class AdminRejectedException extends ServiceException {

    public AdminRejectedException() {
        super(HttpStatus.FORBIDDEN, "승인이 거절된 계정입니다.");
    }
}
