package com.spartabugkiller.ecommercebackofficeproject.admin.dto.response;

import com.spartabugkiller.ecommercebackofficeproject.admin.entity.Admin;
import com.spartabugkiller.ecommercebackofficeproject.admin.entity.AdminRole;
import com.spartabugkiller.ecommercebackofficeproject.admin.entity.AdminStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SigninAdminResponse {

    private final Long id;
    private final String name;
    private final String email;
    private final AdminRole role;
    private final AdminStatus status;
    private final LocalDateTime createdAt;

    public SigninAdminResponse(Long id, String name, String email, AdminRole role, AdminStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.status = status;
        this.createdAt = createdAt;
    }

    public static SigninAdminResponse from(Admin admin) {
        return new SigninAdminResponse(
                admin.getId(),
                admin.getName(),
                admin.getEmail(),
                admin.getRole(),
                admin.getStatus(),
                admin.getCreatedAt()
        );
    }
}
