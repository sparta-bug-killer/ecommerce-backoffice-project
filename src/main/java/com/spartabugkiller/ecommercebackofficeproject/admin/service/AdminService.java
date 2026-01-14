package com.spartabugkiller.ecommercebackofficeproject.admin.service;

import com.spartabugkiller.ecommercebackofficeproject.admin.dto.response.AdminDetailResponse;
import com.spartabugkiller.ecommercebackofficeproject.admin.entity.Admin;
import com.spartabugkiller.ecommercebackofficeproject.admin.exception.AdminNotFoundException;
import com.spartabugkiller.ecommercebackofficeproject.admin.repository.AdminRepository;
import com.spartabugkiller.ecommercebackofficeproject.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    public AdminDetailResponse getAdmin(Long adminId) {
        // session 유저의 권환 학인 후 찾는다
        Admin admin = findById(adminId);
        return AdminDetailResponse.from(admin);
    }

    public Admin findById(Long adminId) {
        return adminRepository.findById(adminId).orElseThrow(
                () -> new AdminNotFoundException(ErrorCode.ADMIN_NOT_FOUND)
        );
    }
}
