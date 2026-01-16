package com.spartabugkiller.ecommercebackofficeproject.review.exception;

import com.spartabugkiller.ecommercebackofficeproject.global.exception.ErrorCode;
import com.spartabugkiller.ecommercebackofficeproject.global.exception.ServiceException;

public class ReviewNotFoundException extends ServiceException {
    public ReviewNotFoundException(ErrorCode errorCode) {
        super(errorCode.getStatus(), errorCode.getMessage());
    }
}
