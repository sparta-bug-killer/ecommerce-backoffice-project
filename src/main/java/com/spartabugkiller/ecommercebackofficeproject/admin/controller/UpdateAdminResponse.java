package com.spartabugkiller.ecommercebackofficeproject.admin.controller;

import com.spartabugkiller.ecommercebackofficeproject.admin.entity.Admin;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateAdminResponse {

    private final String name;
    private final String email;
    private final String phoneNumber;

    public static UpdateAdminResponse from(Admin admin) {
        return UpdateAdminResponse.builder()
                .name(admin.getName())
                .email(admin.getEmail())
                .phoneNumber(admin.getPhoneNumber())
                .build();
    }
}
