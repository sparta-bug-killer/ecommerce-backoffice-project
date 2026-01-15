package com.spartabugkiller.ecommercebackofficeproject.admin.dto.request;

import com.spartabugkiller.ecommercebackofficeproject.admin.entity.Admin;
import com.spartabugkiller.ecommercebackofficeproject.admin.entity.AdminRole;
import lombok.Getter;

@Getter
public class AdminResponse {
    private Long id;
    private String email;
    private String phoneNumber;
    private AdminRole role;

    public AdminResponse(Admin admin) {
        this.id = admin.getId();
        this.email = admin.getEmail();
        this.phoneNumber = admin.getPhoneNumber();
        this.role = admin.getRole();
    }
}