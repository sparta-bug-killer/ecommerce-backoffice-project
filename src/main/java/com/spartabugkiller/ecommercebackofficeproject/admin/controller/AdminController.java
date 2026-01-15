package com.spartabugkiller.ecommercebackofficeproject.admin.controller;

import com.spartabugkiller.ecommercebackofficeproject.admin.dto.request.UpdateAdminPasswordRequest;
import com.spartabugkiller.ecommercebackofficeproject.admin.dto.request.UpdateAdminPasswordRequest;

import com.spartabugkiller.ecommercebackofficeproject.admin.dto.SessionAdmin;

import com.spartabugkiller.ecommercebackofficeproject.admin.dto.request.SigninAdminRequest;
import com.spartabugkiller.ecommercebackofficeproject.admin.dto.request.SignupAdminRequest;
import com.spartabugkiller.ecommercebackofficeproject.admin.dto.response.*;
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

    @GetMapping("/{adminId}")
    public ResponseEntity<GetAdminDetailResponse> getAdmin(@PathVariable Long adminId) {
        GetAdminDetailResponse response = adminService.getAdmin(adminId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{adminId}")
    public ResponseEntity<UpdateAdminResponse> updateAdmin(
            @Valid @RequestBody UpdateAdminRequest request,
            @PathVariable Long adminId
    ) {
        UpdateAdminResponse response = adminService.updateInfo(request, adminId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{adminId}/roles")
    public ResponseEntity<UpdateAdminRoleResponse>  updateAdminRole(
            @Valid @RequestBody UpdateAdminRoleRequest request,
            @PathVariable Long adminId
            ) {
        UpdateAdminRoleResponse response = adminService.updateRole(request, adminId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{adminId}/status")
    public ResponseEntity<UpdateAdminStatusResponse> updateAdminStatus(
            @Valid @RequestBody UpdateAdminStatusRequest request,
            @PathVariable Long adminId
    ) {
        UpdateAdminStatusResponse response = adminService.updateStatus(request, adminId);
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
}
