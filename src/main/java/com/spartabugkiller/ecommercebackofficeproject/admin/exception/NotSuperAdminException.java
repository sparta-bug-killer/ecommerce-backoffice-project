package com.spartabugkiller.ecommercebackofficeproject.admin.exception;

import com.spartabugkiller.ecommercebackofficeproject.global.exception.ErrorCode;
import com.spartabugkiller.ecommercebackofficeproject.global.exception.ServiceException;

public class NotSuperAdminException extends ServiceException {
    public NotSuperAdminException(ErrorCode errorCode) {
        super(errorCode.getStatus(), errorCode.getMessage());
    }
}
