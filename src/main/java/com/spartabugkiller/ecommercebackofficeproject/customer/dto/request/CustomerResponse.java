package com.spartabugkiller.ecommercebackofficeproject.customer.dto.request;

import com.spartabugkiller.ecommercebackofficeproject.customer.entity.Customer;
import com.spartabugkiller.ecommercebackofficeproject.customer.entity.CustomerStatus;
import lombok.Getter;

@Getter
public class CustomerResponse {
    private final Long id;
    private final String name;
    private final String email;
    private final CustomerStatus status;

    // 엔티티를 받아서 DTO로 변환해주는 생성자
    public CustomerResponse(Customer customer) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.email = customer.getEmail();
        this.status = customer.getStatus();
    }
}