package com.spartabugkiller.ecommercebackofficeproject.admin.exception;

import com.spartabugkiller.ecommercebackofficeproject.global.exception.ErrorCode;
import com.spartabugkiller.ecommercebackofficeproject.global.exception.ServiceException;

public class AdminPendingException extends ServiceException {

    public AdminPendingException() {
        super( ErrorCode.ADMIN_PENDING.getStatus(), ErrorCode.ADMIN_PENDING.getMessage());
    }
}
