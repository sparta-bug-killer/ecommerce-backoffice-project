package com.spartabugkiller.ecommercebackofficeproject.order.exception;

import com.spartabugkiller.ecommercebackofficeproject.global.exception.ErrorCode;
import com.spartabugkiller.ecommercebackofficeproject.global.exception.ServiceException;

public class OrderOutOfStockException extends ServiceException {
    public OrderOutOfStockException(ErrorCode errorCode) {
        super(errorCode.getStatus(), errorCode.getMessage());
    }
}