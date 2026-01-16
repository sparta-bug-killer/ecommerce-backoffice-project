package com.spartabugkiller.ecommercebackofficeproject.order.exception;

import com.spartabugkiller.ecommercebackofficeproject.global.exception.ErrorCode;
import com.spartabugkiller.ecommercebackofficeproject.global.exception.ServiceException;

public class OrderCustomerNotFoundException extends ServiceException {
    public OrderCustomerNotFoundException(ErrorCode errorCode) {
        super(errorCode.getStatus(), errorCode.getMessage());
    }
}