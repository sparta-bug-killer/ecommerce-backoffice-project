package com.spartabugkiller.ecommercebackofficeproject.admin.exception;

import com.spartabugkiller.ecommercebackofficeproject.global.exception.ErrorCode;
import com.spartabugkiller.ecommercebackofficeproject.global.exception.ServiceException;

public class AdminRejectedException extends ServiceException {

    public AdminRejectedException() {
        super( ErrorCode.ADMIN_REJECTED.getStatus(), ErrorCode.ADMIN_REJECTED.getMessage());
    }
}
