package com.spartabugkiller.ecommercebackofficeproject.admin.dto.response;

import com.spartabugkiller.ecommercebackofficeproject.admin.entity.Admin;
import com.spartabugkiller.ecommercebackofficeproject.admin.entity.AdminStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApproveAdminResponse {

    private final Long id;
    private final AdminStatus status;

    public static ApproveAdminResponse from(Admin admin) {
        return ApproveAdminResponse.builder()
                .id(admin.getId())
                .status(admin.getStatus())
                .build();
    }
}
