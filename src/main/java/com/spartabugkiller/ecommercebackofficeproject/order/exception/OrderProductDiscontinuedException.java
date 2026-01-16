package com.spartabugkiller.ecommercebackofficeproject.order.exception;

import com.spartabugkiller.ecommercebackofficeproject.global.exception.ErrorCode;
import com.spartabugkiller.ecommercebackofficeproject.global.exception.ServiceException;

public class OrderProductDiscontinuedException extends ServiceException {
    public OrderProductDiscontinuedException(ErrorCode errorCode) {
        super(errorCode.getStatus(), errorCode.getMessage());
    }
}