package com.spartabugkiller.ecommercebackofficeproject.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // amin exception
    ADMIN_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 관리자를 찾을 수 없습니다."),

    // product exception
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 카테고리입니다."),
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 상품입니다."),
    INVALID_PRODUCT_NAME(HttpStatus.BAD_REQUEST, "상품명은 비어 있을 수 없습니다."),
    INVALID_PRODUCT_PRICE(HttpStatus.BAD_REQUEST, "가격은 0 이상이어야 합니다."),
    INVALID_PRODUCT_CATEGORY(HttpStatus.BAD_REQUEST, "카테고리는 필수입니다.");

    private final HttpStatus status;
    private final String message;
}
