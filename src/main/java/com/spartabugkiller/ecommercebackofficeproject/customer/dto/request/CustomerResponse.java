package com.spartabugkiller.ecommercebackofficeproject.customer.dto.request;

import com.spartabugkiller.ecommercebackofficeproject.customer.entity.Customer;
import lombok.Getter;

@Getter
public class CustomerResponse {
    private final Long id;
    private final String username;
    private final String email;

    public CustomerResponse(Customer customer) {
        this.id = customer.getId();
        this.username = customer.getUsername();
        this.email = customer.getEmail();
    }
}