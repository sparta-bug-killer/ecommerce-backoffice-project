package com.spartabugkiller.ecommercebackofficeproject.admin.service;

import com.spartabugkiller.ecommercebackofficeproject.admin.dto.request.SignupAdminRequest;
import com.spartabugkiller.ecommercebackofficeproject.admin.dto.response.AdminDetailResponse;
import com.spartabugkiller.ecommercebackofficeproject.admin.dto.response.SignupAdminResponse;
import com.spartabugkiller.ecommercebackofficeproject.admin.entity.Admin;
import com.spartabugkiller.ecommercebackofficeproject.admin.exception.AdminNotFoundException;
import com.spartabugkiller.ecommercebackofficeproject.admin.repository.AdminRepository;
import com.spartabugkiller.ecommercebackofficeproject.global.config.PasswordEncoder;
import com.spartabugkiller.ecommercebackofficeproject.global.exception.AdminEmailAlreadyExistsException;
import com.spartabugkiller.ecommercebackofficeproject.global.exception.ErrorCode;
import jakarta.transaction.Transactional;
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
