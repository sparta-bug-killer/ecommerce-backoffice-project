package com.spartabugkiller.ecommercebackofficeproject.admin.dto.response;

import com.spartabugkiller.ecommercebackofficeproject.admin.entity.Admin;
import com.spartabugkiller.ecommercebackofficeproject.admin.entity.AdminRole;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateAdminRoleResponse {

    private final Long id;
    private final AdminRole role;

    public static UpdateAdminRoleResponse from(Admin admin) {
        return UpdateAdminRoleResponse.builder()
                .id(admin.getId())
                .role(admin.getRole())
                .build();
    }
}
