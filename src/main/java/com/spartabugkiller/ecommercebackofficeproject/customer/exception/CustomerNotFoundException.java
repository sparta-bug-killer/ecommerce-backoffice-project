package com.spartabugkiller.ecommercebackofficeproject.customer.exception;

import com.spartabugkiller.ecommercebackofficeproject.global.exception.ErrorCode;
import com.spartabugkiller.ecommercebackofficeproject.global.exception.ServiceException;
import lombok.Getter;


public class CustomerNotFoundException extends ServiceException {


    public CustomerNotFoundException(ErrorCode errorCode) {
        super(errorCode.getStatus(), errorCode.getMessage());

    }
}