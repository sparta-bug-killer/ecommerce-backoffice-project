package com.spartabugkiller.ecommercebackofficeproject.admin.service;

import com.spartabugkiller.ecommercebackofficeproject.admin.dto.request.SignupAdminRequest;
import com.spartabugkiller.ecommercebackofficeproject.admin.dto.response.SignupAdminResponse;
import com.spartabugkiller.ecommercebackofficeproject.admin.dto.request.UpdateAdminRequest;
import com.spartabugkiller.ecommercebackofficeproject.admin.dto.request.UpdateAdminRoleRequest;
import com.spartabugkiller.ecommercebackofficeproject.admin.dto.request.UpdateAdminStatusRequest;
import com.spartabugkiller.ecommercebackofficeproject.admin.dto.response.GetAdminDetailResponse;
import com.spartabugkiller.ecommercebackofficeproject.admin.dto.response.UpdateAdminResponse;
import com.spartabugkiller.ecommercebackofficeproject.admin.dto.response.UpdateAdminRoleResponse;
import com.spartabugkiller.ecommercebackofficeproject.admin.dto.response.UpdateAdminStatusResponse;
import com.spartabugkiller.ecommercebackofficeproject.admin.entity.Admin;
import com.spartabugkiller.ecommercebackofficeproject.admin.exception.AdminNotFoundException;
import com.spartabugkiller.ecommercebackofficeproject.admin.repository.AdminRepository;
import com.spartabugkiller.ecommercebackofficeproject.global.config.PasswordEncoder;
import com.spartabugkiller.ecommercebackofficeproject.global.exception.AdminEmailAlreadyExistsException;
import com.spartabugkiller.ecommercebackofficeproject.global.exception.ErrorCode;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    // 비밀번호 암호화를 위한 Spring Security 제공 PasswordEncoder
    private final PasswordEncoder passwordEncoder;

    // 회원가입 로직
    @Transactional
    public SignupAdminResponse signup(SignupAdminRequest request) {

        // 이메일 중복 검사
        if (adminRepository.existsByEmail(request.getEmail())) {
            throw new AdminEmailAlreadyExistsException();
        }

        // 비밀번호 암호화
        String encodedPw = passwordEncoder.encode(request.getPassword());

        // Admin 엔티티 생성
        Admin admin = new Admin(
                request.getName(),
                request.getEmail(),
                encodedPw,
                request.getPhoneNumber(),
                request.getRole()
        );

        // DB에 관리자 계정 저장
        Admin saved = adminRepository.save(admin);
        // 저장된 Admin 엔티티를 Response DTO로 변환해서 반환
        return SignupAdminResponse.from(saved);
    }

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

    @Transactional
    public UpdateAdminStatusResponse updateStatus(@Valid UpdateAdminStatusRequest request, Long adminId) {
        // session 유저의 권한을 확인 후 업데이트

        Admin admin = findById(adminId);
        admin.updateStatus(request);
        return UpdateAdminStatusResponse.from(admin);
    }

    public Admin findById(Long adminId) {
        return adminRepository.findById(adminId).orElseThrow(
                () -> new AdminNotFoundException(ErrorCode.ADMIN_NOT_FOUND)
        );
    }
}
