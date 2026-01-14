package com.spartabugkiller.ecommercebackofficeproject.admin.dto.request;

import com.spartabugkiller.ecommercebackofficeproject.admin.entity.AdminRole;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupAdminRequest {

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
    private String password;

    @NotBlank
    @Pattern(regexp = "^[0-9\\-]+$", message = "전화번호 형식이 올바르지 않습니다.")
    private String phoneNumber;

    @NotNull
    private AdminRole role;
}
