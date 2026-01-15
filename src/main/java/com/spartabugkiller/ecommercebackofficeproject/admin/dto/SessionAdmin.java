package com.spartabugkiller.ecommercebackofficeproject.admin.dto;

import com.spartabugkiller.ecommercebackofficeproject.admin.entity.Admin;
import com.spartabugkiller.ecommercebackofficeproject.admin.entity.AdminRole;
import lombok.Getter;

@Getter
public class SessionAdmin {

    private final Long id;
    private final String name;
    private final String email;
    private final AdminRole role;

    public SessionAdmin(Long id, String name,String email, AdminRole role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public static SessionAdmin from(Admin admin) {
        return new SessionAdmin(admin.getId(), admin.getName(), admin.getEmail(), admin.getRole());
    }
}
