package com.spartabugkiller.ecommercebackofficeproject.customer.dto.request;

import com.spartabugkiller.ecommercebackofficeproject.customer.entity.Customer;
import com.spartabugkiller.ecommercebackofficeproject.customer.entity.CustomerStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CustomerResponse {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private CustomerStatus status;
    private LocalDateTime createdAt;

    public CustomerResponse(Customer customer) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.email = customer.getEmail();
        this.phoneNumber = customer.getPhoneNumber();
        this.status = customer.getStatus();
        this.createdAt = customer.getCreatedAt();
    }
}