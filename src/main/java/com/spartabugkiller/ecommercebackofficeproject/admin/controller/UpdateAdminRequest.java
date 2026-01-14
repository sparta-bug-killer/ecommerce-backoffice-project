package com.spartabugkiller.ecommercebackofficeproject.admin.controller;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UpdateAdminRequest {


    private String name;

    @Email(message = "올바른 이메일 형식이어야 합니다.")
    private String email;

    @Pattern(regexp = "^[0-9\\-]+$", message = "전화번호 형식이 올바르지 않습니다.")
    private String phoneNumber;
}
