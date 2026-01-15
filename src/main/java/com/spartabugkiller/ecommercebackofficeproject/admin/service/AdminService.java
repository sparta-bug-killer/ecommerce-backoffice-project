package com.spartabugkiller.ecommercebackofficeproject.admin.service;

import com.spartabugkiller.ecommercebackofficeproject.admin.dto.SessionAdmin;
import com.spartabugkiller.ecommercebackofficeproject.admin.dto.request.*;
import com.spartabugkiller.ecommercebackofficeproject.admin.dto.response.*;
import com.spartabugkiller.ecommercebackofficeproject.admin.entity.Admin;
import com.spartabugkiller.ecommercebackofficeproject.admin.entity.AdminStatus;
import com.spartabugkiller.ecommercebackofficeproject.admin.exception.*;
import com.spartabugkiller.ecommercebackofficeproject.admin.repository.AdminRepository;
import com.spartabugkiller.ecommercebackofficeproject.global.config.PasswordEncoder;
import jakarta.servlet.http.HttpSession;
import com.spartabugkiller.ecommercebackofficeproject.global.exception.ErrorCode;
import org.springframework.transaction.annotation.Transactional;
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

    // 로그인 로직
    @Transactional(readOnly = true)
    public SigninAdminResponse signin(SigninAdminRequest request, HttpSession session) {

        // 이메일 검증
        Admin admin = adminRepository.findByEmail(request.getEmail())
                .orElseThrow(AdminInvalidEmailException::new);

        // 비밀번호 검증
        if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
            throw new AdminInvalidPasswordException();
        }

        // 탈퇴 여부 먼저 확인 (존재 자체가 없음)
        if (admin.getDeletedAt() != null) {
            throw new AdminInactiveException();
        }

        // 승인 대기 상태
        if (admin.getStatus() == AdminStatus.PENDING) {
            throw new AdminPendingException();
        }

        // 승인 거절 상태
        if (admin.getStatus() == AdminStatus.REJECTED) {
            throw new AdminRejectedException();
        }

        // 계정 정지 상태
        if (admin.getStatus() == AdminStatus.BLOCKED) {
            throw new AdminBlockedException();
        }

        // 계정 비활성화 상태
        if (admin.getStatus() == AdminStatus.INACTIVE) {
            throw new AdminInactiveException();
        }

        // 세션에 로그인 정보 저장
        session.setAttribute("LOGIN_ADMIN", SessionAdmin.from(admin));

        // 승인 처리 시 Response DTO로 변환해서 반환
        return new SigninAdminResponse(
                admin.getId(),
                admin.getName(),
                admin.getEmail(),
                admin.getRole(),
                admin.getStatus(),
                admin.getCreatedAt()
        );
    }

    // 로그아웃 로직
    @Transactional
    public void logout(HttpSession session) {
        session.invalidate();
    }

    @Transactional(readOnly = true)
    public GetAdminDetailResponse getAdmin(Long adminId) {
        // session 유저의 권환 학인 후 찾는다
        Admin admin = findById(adminId);
        return GetAdminDetailResponse.from(admin);
    }

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
    public UpdateAdminStatusResponse updateStatus(UpdateAdminStatusRequest request, Long adminId) {
        // session 유저의 권한을 확인 후 업데이트

        Admin admin = findById(adminId);
        admin.updateStatus(request);
        return UpdateAdminStatusResponse.from(admin);
    }

    @Transactional
    public void deleteAdmin(Long adminId) {
        // session 유저의 권한을 확인 후 삭제

        Admin admin = findById(adminId);
        admin.delete();
    }

    @Transactional
    public ApproveAdminResponse approveAdmin(Long adminId, ApproveAdminRequest request) {
        // session 유저의 권한을 확인 후 업데이트

        Admin admin = findById(adminId);
        if (request.getStatus() == AdminStatus.REJECTED) {
            admin.markAsRejected(request);
        }

        if (request.getStatus() == AdminStatus.APPROVED) {
            admin.markAsApproved(request, 1L);
        }

        return ApproveAdminResponse.from(admin);
    }

    public Admin findById(Long adminId) {
        return adminRepository.findById(adminId).orElseThrow(
                () -> new AdminNotFoundException(ErrorCode.ADMIN_NOT_FOUND)
        );
    }
}
