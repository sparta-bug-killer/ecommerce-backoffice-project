package com.spartabugkiller.ecommercebackofficeproject.admin.exception;

import com.spartabugkiller.ecommercebackofficeproject.global.exception.ErrorCode;
import com.spartabugkiller.ecommercebackofficeproject.global.exception.ServiceException;

public class AdminBlockedException extends ServiceException {
    public AdminBlockedException() {
        super(ErrorCode.ADMIN_BLOCKED.getStatus(), ErrorCode.ADMIN_BLOCKED.getMessage());
    }
}
