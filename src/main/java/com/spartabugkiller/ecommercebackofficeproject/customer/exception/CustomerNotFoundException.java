package com.spartabugkiller.ecommercebackofficeproject.customer.exception;

import com.spartabugkiller.ecommercebackofficeproject.global.exception.ErrorCode;
import com.spartabugkiller.ecommercebackofficeproject.global.exception.ServiceException;
import lombok.Getter;

@Getter
public class CustomerNotFoundException extends ServiceException {
    private final ErrorCode errorCode;

    public CustomerNotFoundException(ErrorCode errorCode) {

        super(errorCode.getStatus(), errorCode.getMessage());
        this.errorCode = errorCode;
    }
}