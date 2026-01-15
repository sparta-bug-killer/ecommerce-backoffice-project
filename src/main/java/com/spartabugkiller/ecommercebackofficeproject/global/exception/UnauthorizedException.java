package com.spartabugkiller.ecommercebackofficeproject.global.exception;

public class UnauthorizedException extends ServiceException {
    public UnauthorizedException(ErrorCode errorCode) {
        super(errorCode.getStatus(), errorCode.getMessage());
    }
}
