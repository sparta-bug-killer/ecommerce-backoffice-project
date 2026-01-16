package com.spartabugkiller.ecommercebackofficeproject.order.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    READY,
    SHIPPING,
    COMPLETED,
    CANCELED
}
