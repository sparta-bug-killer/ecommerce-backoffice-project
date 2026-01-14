package com.spartabugkiller.ecommercebackofficeproject.customer.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CustomerRequest {
    private String username;
    private String email;
    private String password;
}