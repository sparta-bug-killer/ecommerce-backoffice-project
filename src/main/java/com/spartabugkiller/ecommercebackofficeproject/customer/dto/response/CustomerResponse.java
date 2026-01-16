package com.spartabugkiller.ecommercebackofficeproject.customer.dto.response;

import com.spartabugkiller.ecommercebackofficeproject.customer.entity.Customer;
import lombok.Getter;

@Getter
public class CustomerResponse {
    private final Long id;
    private final String username;
    private final String email;

    // [도전 기능] 주문 기반 집계 필드 추가
    private final Long totalOrderCount;
    private final Long totalOrderAmount;

    // 서비스에서 계산된 통계 데이터를 함께 받아 생성합니다.
    public CustomerResponse(Customer customer, Long totalOrderCount, Long totalOrderAmount) {
        this.id = customer.getId();
        this.username = customer.getUsername();
        this.email = customer.getEmail();
        this.totalOrderCount = totalOrderCount;
        this.totalOrderAmount = totalOrderAmount;
    }
}