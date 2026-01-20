package com.spartabugkiller.ecommercebackofficeproject.order.entity;

import lombok.Getter;

@Getter
public enum OrderStatus {
    READY("상품준비중"),
    SHIPPING("배송중"),
    COMPLETED("배송완료"),
    CANCELED("주문취소");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }
}
