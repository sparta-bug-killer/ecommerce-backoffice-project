package com.spartabugkiller.ecommercebackofficeproject.admin.dto.response;

import com.spartabugkiller.ecommercebackofficeproject.admin.entity.Admin;
import com.spartabugkiller.ecommercebackofficeproject.admin.entity.AdminRole;
import com.spartabugkiller.ecommercebackofficeproject.admin.entity.AdminStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SignupAdminResponse {

    private final long id;
    private final String name;
    private final String email;
    private final String phoneNumber;
    private final AdminRole role;
    private final AdminStatus status;
    private final LocalDateTime createdAt;

    public SignupAdminResponse(long id, String name, String email, String phoneNumber, AdminRole role, AdminStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.status = status;
        this.createdAt = createdAt;
    }

    public static SignupAdminResponse from(Admin admin) {
        return new SignupAdminResponse(
                admin.getId(),
                admin.getName(),
                admin.getEmail(),
                admin.getPhoneNumber(),
                admin.getRole(),
                admin.getStatus(),
                admin.getCreatedAt()
        );
    }
}
