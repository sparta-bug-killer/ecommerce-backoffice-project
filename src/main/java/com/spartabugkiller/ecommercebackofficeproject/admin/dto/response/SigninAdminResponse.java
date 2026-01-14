package com.spartabugkiller.ecommercebackofficeproject.admin.dto.response;

import com.spartabugkiller.ecommercebackofficeproject.admin.entity.Admin;
import com.spartabugkiller.ecommercebackofficeproject.admin.entity.AdminRole;
import com.spartabugkiller.ecommercebackofficeproject.admin.entity.AdminStatus;
import lombok.Getter;

@Getter
public class SigninAdminResponse {

    private final Long id;
    private final String name;
    private final String email;
    private final AdminRole role;
    private final AdminStatus status;

    public SigninAdminResponse(Long id, String name, String email, AdminRole role, AdminStatus status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.status = status;
    }

    public static SigninAdminResponse from(Admin admin) {
        return new SigninAdminResponse(
                admin.getId(),
                admin.getName(),
                admin.getEmail(),
                admin.getRole(),
                admin.getStatus()
        );
    }
}
