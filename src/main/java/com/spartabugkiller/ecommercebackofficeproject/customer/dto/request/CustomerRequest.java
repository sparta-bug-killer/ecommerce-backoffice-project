package com.spartabugkiller.ecommercebackofficeproject.customer.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomerRequest {
    private String username;
    private String email;
    private String phone;
}
