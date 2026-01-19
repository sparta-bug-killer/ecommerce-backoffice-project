package com.spartabugkiller.ecommercebackofficeproject.customer.dto.response;

import com.spartabugkiller.ecommercebackofficeproject.customer.entity.Customer;
import com.spartabugkiller.ecommercebackofficeproject.customer.entity.CustomerStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CustomerResponse {
    private final Long id;
    private final String username;
    private final String email;
    private final String phone;
    private final CustomerStatus status;
    private final LocalDateTime createdAt;
    private final Long totalOrderCount;
    private final Long totalAmount;

    public CustomerResponse(Customer customer, Long totalOrderCount, Long totalAmount) {
        this.id = customer.getId();
        this.username = customer.getUsername();
        this.email = customer.getEmail();
        this.phone = customer.getPhone();
        this.status = customer.getStatus();
        this.createdAt = customer.getCreatedAt();
        this.totalOrderCount = totalOrderCount != null ? totalOrderCount : 0L;
        this.totalAmount = totalAmount != null ? totalAmount : 0L;
    }
}