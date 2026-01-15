package com.spartabugkiller.ecommercebackofficeproject.admin.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateAdminPasswordRequest {
    private String oldPassword;
    private String newPassword;
}