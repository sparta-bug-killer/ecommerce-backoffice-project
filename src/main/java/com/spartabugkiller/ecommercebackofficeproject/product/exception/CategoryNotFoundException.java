package com.spartabugkiller.ecommercebackofficeproject.product.exception;

import com.spartabugkiller.ecommercebackofficeproject.global.exception.ErrorCode;
import com.spartabugkiller.ecommercebackofficeproject.global.exception.ServiceException;

public class CategoryNotFoundException extends ServiceException {
    public CategoryNotFoundException(ErrorCode errorCode) {
        super(errorCode.getStatus(), errorCode.getMessage());
    }
}