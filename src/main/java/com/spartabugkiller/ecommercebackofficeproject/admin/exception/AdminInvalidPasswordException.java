package com.spartabugkiller.ecommercebackofficeproject.admin.exception;

import com.spartabugkiller.ecommercebackofficeproject.global.exception.ErrorCode;
import com.spartabugkiller.ecommercebackofficeproject.global.exception.ServiceException;

public class AdminInvalidPasswordException extends ServiceException {

    public AdminInvalidPasswordException() {
        super( ErrorCode.ADMIN_INVALID_PASSWORD.getStatus(), ErrorCode.ADMIN_INVALID_PASSWORD.getMessage());
    }
}
