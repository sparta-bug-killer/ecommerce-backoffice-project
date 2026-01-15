package com.spartabugkiller.ecommercebackofficeproject.product.exception;

import com.spartabugkiller.ecommercebackofficeproject.global.exception.ErrorCode;
import com.spartabugkiller.ecommercebackofficeproject.global.exception.ServiceException;

public class ProductInvalidNameException extends ServiceException {
    public ProductInvalidNameException(ErrorCode errorCode) {
        super(errorCode.getStatus(), errorCode.getMessage());
    }
}
