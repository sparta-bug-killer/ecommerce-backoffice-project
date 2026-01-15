package com.spartabugkiller.ecommercebackofficeproject.admin.dto.response;

import com.spartabugkiller.ecommercebackofficeproject.admin.entity.Admin;
import com.spartabugkiller.ecommercebackofficeproject.admin.entity.AdminRole;
import com.spartabugkiller.ecommercebackofficeproject.admin.entity.AdminStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder(access = AccessLevel.PROTECTED)
public class GetAdminsDetailResponse {

    private final Long id;
    private final String name;
    private final String email;
    private final String phoneNumber;
    private final AdminRole adminRole;
    private final AdminStatus adminStatus;
    private final LocalDateTime createdAt;
    private final LocalDateTime approvedAt;

    public static GetAdminsDetailResponse from(Admin admin) {
        return GetAdminsDetailResponse.builder()
                .id(admin.getId())
                .name(admin.getName())
                .email(admin.getEmail())
                .phoneNumber(admin.getPhoneNumber())
                .adminRole(admin.getRole())
                .adminStatus(admin.getStatus())
                .createdAt(admin.getCreatedAt())
                .approvedAt(admin.getApprovedAt())
                .build();
    }
}
