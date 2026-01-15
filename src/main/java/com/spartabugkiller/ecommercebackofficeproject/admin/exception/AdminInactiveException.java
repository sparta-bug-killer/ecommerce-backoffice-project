package com.spartabugkiller.ecommercebackofficeproject.admin.exception;

import com.spartabugkiller.ecommercebackofficeproject.global.exception.ServiceException;
import org.springframework.http.HttpStatus;

public class AdminInactiveException extends ServiceException {

    public AdminInactiveException() {
        super(HttpStatus.FORBIDDEN, "비활성화된 관리자 계정입니다.");
    }
}
