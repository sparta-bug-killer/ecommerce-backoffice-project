package com.spartabugkiller.ecommercebackofficeproject.product.exception;

import com.spartabugkiller.ecommercebackofficeproject.global.exception.ErrorCode;
import com.spartabugkiller.ecommercebackofficeproject.global.exception.ServiceException;

public class ProductInvalidCategoryException extends ServiceException {
    public ProductInvalidCategoryException(ErrorCode errorCode) {
        super(errorCode.getStatus(), errorCode.getMessage());
    }
}

