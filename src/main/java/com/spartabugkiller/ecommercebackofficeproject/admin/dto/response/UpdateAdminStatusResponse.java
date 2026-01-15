package com.spartabugkiller.ecommercebackofficeproject.admin.dto.response;

import com.spartabugkiller.ecommercebackofficeproject.admin.entity.Admin;
import com.spartabugkiller.ecommercebackofficeproject.admin.entity.AdminStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateAdminStatusResponse {

    private final Long id;
    private final AdminStatus status;

    public static UpdateAdminStatusResponse from(Admin admin){
        return UpdateAdminStatusResponse.builder()
                .id(admin.getId())
                .status(admin.getStatus())
                .build();
    }
}
