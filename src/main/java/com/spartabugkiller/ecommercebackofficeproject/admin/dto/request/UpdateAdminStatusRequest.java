package com.spartabugkiller.ecommercebackofficeproject.admin.dto.request;

import com.spartabugkiller.ecommercebackofficeproject.admin.entity.AdminStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateAdminStatusRequest {

    @NotNull
    private AdminStatus status;
}
