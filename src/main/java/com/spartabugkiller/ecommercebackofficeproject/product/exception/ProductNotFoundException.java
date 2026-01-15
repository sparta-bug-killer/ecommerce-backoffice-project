package com.spartabugkiller.ecommercebackofficeproject.product.exception;

import com.spartabugkiller.ecommercebackofficeproject.global.exception.ErrorCode;
import com.spartabugkiller.ecommercebackofficeproject.global.exception.ServiceException;

public class ProductNotFoundException extends ServiceException {
    public ProductNotFoundException(ErrorCode errorCode) {
        super(errorCode.getStatus(), errorCode.getMessage());
    }
}
