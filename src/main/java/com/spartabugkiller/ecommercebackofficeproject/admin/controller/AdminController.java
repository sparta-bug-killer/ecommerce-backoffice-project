package com.spartabugkiller.ecommercebackofficeproject.admin.controller;

import com.spartabugkiller.ecommercebackofficeproject.admin.dto.SessionAdmin;
import com.spartabugkiller.ecommercebackofficeproject.admin.dto.request.*;
import com.spartabugkiller.ecommercebackofficeproject.admin.dto.response.*;
import com.spartabugkiller.ecommercebackofficeproject.admin.entity.AdminRole;
import com.spartabugkiller.ecommercebackofficeproject.admin.entity.AdminStatus;
import com.spartabugkiller.ecommercebackofficeproject.admin.dto.request.UpdateAdminRoleRequest;
import com.spartabugkiller.ecommercebackofficeproject.admin.dto.request.UpdateAdminRequest;
import com.spartabugkiller.ecommercebackofficeproject.admin.dto.request.UpdateAdminStatusRequest;
import com.spartabugkiller.ecommercebackofficeproject.admin.entity.Admin;
import com.spartabugkiller.ecommercebackofficeproject.admin.service.AdminService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins")
public class AdminController {

    private final AdminService adminService;

    // 관리자 회원가입 요청 API
    @PostMapping("/signup")
    public ResponseEntity<SignupAdminResponse> signup(@Valid @RequestBody SignupAdminRequest request) {
        SignupAdminResponse response = adminService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 관리자 로그인 요청 API
    @PostMapping("/signin")
    public ResponseEntity<SigninAdminResponse> signin(
            @Valid @RequestBody SigninAdminRequest request,
            HttpSession session
    ) {
        Admin admin = adminService.signin(request);

        // 세션에 로그인 정보 저장
        session.setAttribute("LOGIN_ADMIN", SessionAdmin.from(admin));

        return ResponseEntity.ok(SigninAdminResponse.from(admin));
    }

    // 관리자 로그아웃 요청 API
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        adminService.logout(session);
        return ResponseEntity.ok().build();
    }

    /**
     * 관리자 리스트 조회 API
     */
    @GetMapping
    public ResponseEntity<List<GetAdminsDetailResponse>> getAdmins(
            @SessionAttribute(name = "LOGIN_ADMIN") SessionAdmin sessionAdmin,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String order,
            @RequestParam(required = false) AdminRole role,
            @RequestParam(required = false) AdminStatus status
    ) {
        System.out.println(sessionAdmin.getEmail());
        int pageIndex = Math.max(page - 1, 0);
        List<GetAdminsDetailResponse> responseList = adminService.getAdmins(sessionAdmin, keyword, pageIndex, size, sortBy, order, role, status);
        return ResponseEntity.ok(responseList);
    }
    /**
     * 관리자 상세 정보 조회 API
     */
    @GetMapping("/{adminId}")
    public ResponseEntity<GetAdminDetailResponse> getAdmin(
            @SessionAttribute(name = "LOGIN_ADMIN") SessionAdmin sessionAdmin,
            @PathVariable Long adminId
    ) {
        GetAdminDetailResponse response = adminService.getAdmin(sessionAdmin, adminId);
        return ResponseEntity.ok(response);
    }

    /**
     * 관리자 정보 수정 API
     */
    @PatchMapping("/{adminId}")
    public ResponseEntity<UpdateAdminResponse> updateAdmin(
            @SessionAttribute(name = "LOGIN_ADMIN") SessionAdmin sessionAdmin,
            @Valid @RequestBody UpdateAdminRequest request,
            @PathVariable Long adminId
    ) {
        UpdateAdminResponse response = adminService.updateInfo(sessionAdmin, request, adminId);
        return ResponseEntity.ok(response);
    }

    /**
     * 관리자 role 변경 API
     */
    @PatchMapping("/{adminId}/roles")
    public ResponseEntity<UpdateAdminRoleResponse>  updateAdminRole(
            @SessionAttribute(name = "LOGIN_ADMIN") SessionAdmin sessionAdmin,
            @Valid @RequestBody UpdateAdminRoleRequest request,
            @PathVariable Long adminId
            ) {
        UpdateAdminRoleResponse response = adminService.updateRole(sessionAdmin, request, adminId);
        return ResponseEntity.ok(response);
    }

    /**
     * 관리자 status 변경 API
     */
    @PatchMapping("/{adminId}/status")
    public ResponseEntity<UpdateAdminStatusResponse> updateAdminStatus(
            @SessionAttribute(name = "LOGIN_ADMIN") SessionAdmin sessionAdmin,
            @Valid @RequestBody UpdateAdminStatusRequest request,
            @PathVariable Long adminId
    ) {
        UpdateAdminStatusResponse response = adminService.updateStatus(sessionAdmin, request, adminId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{adminId}/password")
    public ResponseEntity<UpdateAdminResponse> updateAdminPassword(
            @Valid @RequestBody UpdateAdminPasswordRequest request, // 비밀번호용 DTO가 따로 있어야 합니다!
            @PathVariable Long adminId
    ) {
        UpdateAdminResponse response = adminService.updatePassword(request, adminId);
        return ResponseEntity.ok(response);
    }

    /**
     * 관리자 삭제 API
     */
    @DeleteMapping("/{adminId}")
    public ResponseEntity<Void> deleteAdmin(
            @SessionAttribute(name = "LOGIN_ADMIN") SessionAdmin sessionAdmin,
            @PathVariable Long adminId
    ) {
        adminService.deleteAdmin(sessionAdmin, adminId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 관리자 승인/거부 API
     */
    @PatchMapping("/{adminId}/approve")
    public ResponseEntity<ApproveAdminResponse> approveAdmin(
            @SessionAttribute(name = "LOGIN_ADMIN") SessionAdmin sessionAdmin,
            @PathVariable Long adminId,
            @Valid @RequestBody ApproveAdminRequest request
    ) {
        ApproveAdminResponse response = adminService.approveAdmin(sessionAdmin, adminId, request);
        return ResponseEntity.ok(response);
    }
}
