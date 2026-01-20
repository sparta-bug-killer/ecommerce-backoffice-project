package com.spartabugkiller.ecommercebackofficeproject.admin.exception;

import com.spartabugkiller.ecommercebackofficeproject.global.exception.ErrorCode;
import com.spartabugkiller.ecommercebackofficeproject.global.exception.ServiceException;

public class AdminInactiveException extends ServiceException {

    public AdminInactiveException() {
        super( ErrorCode.ADMIN_INACTIVE.getStatus(), ErrorCode.ADMIN_INACTIVE.getMessage());
    }
}
