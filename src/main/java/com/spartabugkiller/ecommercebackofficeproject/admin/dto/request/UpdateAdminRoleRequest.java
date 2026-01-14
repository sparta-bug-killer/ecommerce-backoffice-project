package com.spartabugkiller.ecommercebackofficeproject.admin.dto.request;

import com.spartabugkiller.ecommercebackofficeproject.admin.entity.AdminRole;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateAdminRoleRequest {

    @NotNull(message = "변경할 Role의 입력값은 필수입니다.")
    private AdminRole role;
}
