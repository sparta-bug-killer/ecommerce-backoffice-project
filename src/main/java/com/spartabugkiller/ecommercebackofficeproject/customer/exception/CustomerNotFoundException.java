package com.spartabugkiller.ecommercebackofficeproject.customer.exception;

import com.spartabugkiller.ecommercebackofficeproject.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class CustomerNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;

    public CustomerNotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}