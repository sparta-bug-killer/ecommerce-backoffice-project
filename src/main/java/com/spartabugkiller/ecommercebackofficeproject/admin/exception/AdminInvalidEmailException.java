package com.spartabugkiller.ecommercebackofficeproject.admin.exception;

import com.spartabugkiller.ecommercebackofficeproject.global.exception.ErrorCode;
import com.spartabugkiller.ecommercebackofficeproject.global.exception.ServiceException;

public class AdminInvalidEmailException extends ServiceException {

    public AdminInvalidEmailException() {
        super( ErrorCode.ADMIN_INVALID_EMAIL.getStatus(), ErrorCode.ADMIN_INVALID_EMAIL.getMessage());
    }
}
