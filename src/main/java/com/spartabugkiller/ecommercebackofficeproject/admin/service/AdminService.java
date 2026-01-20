package com.spartabugkiller.ecommercebackofficeproject.admin.service;

import com.spartabugkiller.ecommercebackofficeproject.admin.dto.request.*;
import com.spartabugkiller.ecommercebackofficeproject.admin.dto.response.*;
import com.spartabugkiller.ecommercebackofficeproject.admin.entity.Admin;
import com.spartabugkiller.ecommercebackofficeproject.admin.entity.AdminRole;
import com.spartabugkiller.ecommercebackofficeproject.admin.entity.AdminStatus;
import com.spartabugkiller.ecommercebackofficeproject.admin.exception.*;
import com.spartabugkiller.ecommercebackofficeproject.admin.repository.AdminRepository;
import com.spartabugkiller.ecommercebackofficeproject.global.common.SessionUtils;
import com.spartabugkiller.ecommercebackofficeproject.global.config.PasswordEncoder;
import com.spartabugkiller.ecommercebackofficeproject.global.dto.PageInfo;
import com.spartabugkiller.ecommercebackofficeproject.global.exception.ErrorCode;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static com.spartabugkiller.ecommercebackofficeproject.global.common.PageableUtils.makePageable;

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
    public Admin signin(SigninAdminRequest request) {

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

        // 승인 처리 시 Response DTO로 변환해서 반환
        return admin;
    }

    // 로그아웃 로직
    @Transactional
    public void logout(HttpSession session) {
        session.invalidate();
    }

    @Transactional(readOnly = true)
    public GetAdminsResponse getAdmins(HttpSession session, String keyword, int page, Integer size, String sortBy, String order, AdminRole role, AdminStatus status) {
        SessionUtils.validateSuperAdmin(session);

        int pageSize = (size == null || size < 1) ? 10 : size;

        Set<String> allowed = Set.of("name","email","createdAt","approvedAt");
        Pageable pageable = makePageable(page, sortBy, order, pageSize, allowed);

        Page<Admin> pageResult = adminRepository.findAllAdmins(keyword, role, status, pageable);

        List<AdminItems> items = pageResult.getContent().stream()
                .map(AdminItems::from)
                .toList();

        PageInfo pageInfo = PageInfo.from(pageResult);

        return GetAdminsResponse.from(items, pageInfo);
    }

    @Transactional(readOnly = true)
    public GetAdminDetailResponse getAdmin(HttpSession session, Long adminId) {
        SessionUtils.validateSuperAdmin(session);
        Admin admin = findById(adminId);
        return GetAdminDetailResponse.from(admin);
    }

    @Transactional
    public UpdateAdminResponse updateInfo(HttpSession session, UpdateAdminRequest request, Long adminId) {
        SessionUtils.validateSuperAdmin(session);
        Admin admin = findById(adminId);
        admin.updateInfo(request);
        return UpdateAdminResponse.from(admin);
    }

    @Transactional
    public UpdateAdminRoleResponse updateRole(HttpSession session, UpdateAdminRoleRequest request, Long adminId) {
        SessionUtils.validateSuperAdmin(session);
        Admin admin = findById(adminId);
        admin.updateRole(request);
        return UpdateAdminRoleResponse.from(admin);
    }

    @Transactional
    public UpdateAdminStatusResponse updateStatus(HttpSession session, UpdateAdminStatusRequest request, Long adminId) {
        SessionUtils.validateSuperAdmin(session);
        Admin admin = findById(adminId);
        admin.updateStatus(request);
        return UpdateAdminStatusResponse.from(admin);
    }

    @Transactional
    public void deleteAdmin(HttpSession session, Long adminId) {
        SessionUtils.validateSuperAdmin(session);
        Admin admin = findById(adminId);
        admin.delete();
    }

    @Transactional
    public ApproveAdminResponse approveAdmin(HttpSession session, Long adminId, ApproveAdminRequest request) {
        SessionUtils.validateSuperAdmin(session);
        Admin admin = findById(adminId);
        if (request.getStatus() == AdminStatus.REJECTED) {
            admin.markAsRejected(request);
        }

        if (request.getStatus() == AdminStatus.APPROVED) {
            admin.markAsApproved(request, 1L);
        }

        return ApproveAdminResponse.from(admin);
    }

    @Transactional
    public UpdateAdminResponse updatePassword(UpdateAdminPasswordRequest request, Long adminId) {
        // 1. 관리자 조회
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new AdminNotFoundException(ErrorCode.ADMIN_NOT_FOUND));

        // 2. 비밀번호 변경 (실제로는 인코딩 로직 등이 들어가야 합니다)
        admin.updatePassword(request.getNewPassword());

        return UpdateAdminResponse.from(admin);
    }

    private Admin findById(Long adminId) {
        return adminRepository.findById(adminId).orElseThrow(
                () -> new AdminNotFoundException(ErrorCode.ADMIN_NOT_FOUND)
        );
    }
}
