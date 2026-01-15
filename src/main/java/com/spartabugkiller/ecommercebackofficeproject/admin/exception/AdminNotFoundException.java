package com.spartabugkiller.ecommercebackofficeproject.admin.exception;

import com.spartabugkiller.ecommercebackofficeproject.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class AdminNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;


    public AdminNotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}