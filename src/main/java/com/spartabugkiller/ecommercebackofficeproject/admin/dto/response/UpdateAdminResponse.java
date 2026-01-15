package com.spartabugkiller.ecommercebackofficeproject.admin.dto.response;

import com.spartabugkiller.ecommercebackofficeproject.admin.entity.Admin;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateAdminResponse {

    private final Long id;
    private final String name;
    private final String email;
    private final String phoneNumber;

    public static UpdateAdminResponse from(Admin admin) {
        return UpdateAdminResponse.builder()
                .id(admin.getId())
                .name(admin.getName())
                .email(admin.getEmail())
                .phoneNumber(admin.getPhoneNumber())
                .build();
    }
}
