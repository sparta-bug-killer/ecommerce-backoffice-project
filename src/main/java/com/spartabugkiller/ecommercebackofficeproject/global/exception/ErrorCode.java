package com.spartabugkiller.ecommercebackofficeproject.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    ADMIN_BLOCKED(HttpStatus.LOCKED, "정지된 계정입니다."),
    ADMIN_PENDING(HttpStatus.FORBIDDEN, "승인 대기 중인 계정입니다."),
    ADMIN_REJECTED(HttpStatus.FORBIDDEN, "승인이 거절된 계정입니다."),
    ADMIN_INACTIVE(HttpStatus.FORBIDDEN, "비활성화된 계정입니다."),
    ADMIN_NOT_FOUND(HttpStatus.NOT_FOUND, "관리자를 찾을 수 없습니다."),
    ADMIN_INVALID_EMAIL(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 올바르지 않습니다."),
    ADMIN_INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 올바르지 않습니다."),
    ADMIN_EMAIL_DUPLICATED(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다."),
    REJECTED_REASON_NOT_FOUND(HttpStatus.NOT_FOUND, "거부 사유는 필수 입력값입니다."),
    NOT_SUPER_ADMIN(HttpStatus.UNAUTHORIZED, "권한이 없는 관리자입니다."),

    // product exception,
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 카테고리입니다."),
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 상품입니다."),
    PRODUCT_DISCONTINUED(HttpStatus.BAD_REQUEST, "단종된 상품은 재고를 변경할 수 없습니다."),

    INVALID_PRODUCT_NAME(HttpStatus.BAD_REQUEST, "상품명은 비어 있을 수 없습니다."),
    INVALID_PRODUCT_PRICE(HttpStatus.BAD_REQUEST, "가격은 0 이상이어야 합니다."),
    INVALID_PRODUCT_CATEGORY(HttpStatus.BAD_REQUEST, "카테고리는 필수입니다.");

    private final HttpStatus status;
    private final String message;
}
