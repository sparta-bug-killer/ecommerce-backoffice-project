package com.spartabugkiller.ecommercebackofficeproject.admin.service;

import com.spartabugkiller.ecommercebackofficeproject.admin.dto.request.UpdateAdminRequest;
import com.spartabugkiller.ecommercebackofficeproject.admin.dto.request.UpdateAdminRoleRequest;
import com.spartabugkiller.ecommercebackofficeproject.admin.dto.response.GetAdminDetailResponse;
import com.spartabugkiller.ecommercebackofficeproject.admin.dto.response.UpdateAdminResponse;
import com.spartabugkiller.ecommercebackofficeproject.admin.dto.response.UpdateAdminRoleResponse;
import com.spartabugkiller.ecommercebackofficeproject.admin.entity.Admin;
import com.spartabugkiller.ecommercebackofficeproject.admin.exception.AdminNotFoundException;
import com.spartabugkiller.ecommercebackofficeproject.admin.repository.AdminRepository;
import com.spartabugkiller.ecommercebackofficeproject.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    @Transactional(readOnly = true)
    public GetAdminDetailResponse getAdmin(Long adminId) {
        // session 유저의 권환 학인 후 찾는다
        Admin admin = findById(adminId);
        return GetAdminDetailResponse.from(admin);
    }

    @Transactional
    public UpdateAdminResponse updateInfo(UpdateAdminRequest request, Long adminId) {
        // session 유저의 권한 확인 후 업데이트

        Admin admin = findById(adminId);
        admin.updateInfo(request);
        return UpdateAdminResponse.from(admin);
    }

    @Transactional
    public UpdateAdminRoleResponse updateRole(UpdateAdminRoleRequest request, Long adminId) {
        // session 유저의 권한을 확인 후 업데이트

        Admin admin = findById(adminId);
        admin.updateRole(request);
        return UpdateAdminRoleResponse.from(admin);
    }

    public Admin findById(Long adminId) {
        return adminRepository.findById(adminId).orElseThrow(
                () -> new AdminNotFoundException(ErrorCode.ADMIN_NOT_FOUND)
        );
    }
}
