package com.spartabugkiller.ecommercebackofficeproject.admin.exception;

import com.spartabugkiller.ecommercebackofficeproject.global.exception.ErrorCode;
import com.spartabugkiller.ecommercebackofficeproject.global.exception.ServiceException;

public class AdminEmailAlreadyExistsException extends ServiceException {

    public AdminEmailAlreadyExistsException() {
        super(ErrorCode.ADMIN_EMAIL_DUPLICATED.getStatus(), ErrorCode.ADMIN_EMAIL_DUPLICATED.getMessage());
    }
}
