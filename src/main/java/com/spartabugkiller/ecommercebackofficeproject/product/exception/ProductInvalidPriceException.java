package com.spartabugkiller.ecommercebackofficeproject.product.exception;

import com.spartabugkiller.ecommercebackofficeproject.global.exception.ErrorCode;
import com.spartabugkiller.ecommercebackofficeproject.global.exception.ServiceException;

public class ProductInvalidPriceException extends ServiceException {
    public ProductInvalidPriceException(ErrorCode errorCode) {
        super(errorCode.getStatus(), errorCode.getMessage());
    }
}

