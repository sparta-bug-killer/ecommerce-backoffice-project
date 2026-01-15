package com.spartabugkiller.ecommercebackofficeproject.admin.controller;

import com.spartabugkiller.ecommercebackofficeproject.admin.dto.request.SigninAdminRequest;
import com.spartabugkiller.ecommercebackofficeproject.admin.dto.request.SignupAdminRequest;
import com.spartabugkiller.ecommercebackofficeproject.admin.dto.response.SigninAdminResponse;
import com.spartabugkiller.ecommercebackofficeproject.admin.dto.response.SignupAdminResponse;
import com.spartabugkiller.ecommercebackofficeproject.admin.dto.request.UpdateAdminRoleRequest;
import com.spartabugkiller.ecommercebackofficeproject.admin.dto.request.UpdateAdminRequest;
import com.spartabugkiller.ecommercebackofficeproject.admin.dto.request.UpdateAdminStatusRequest;
import com.spartabugkiller.ecommercebackofficeproject.admin.dto.response.GetAdminDetailResponse;
import com.spartabugkiller.ecommercebackofficeproject.admin.dto.response.UpdateAdminResponse;
import com.spartabugkiller.ecommercebackofficeproject.admin.dto.response.UpdateAdminRoleResponse;
import com.spartabugkiller.ecommercebackofficeproject.admin.dto.response.UpdateAdminStatusResponse;
import com.spartabugkiller.ecommercebackofficeproject.admin.dto.request.*;
import com.spartabugkiller.ecommercebackofficeproject.admin.dto.response.*;
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
            @Valid @RequestBody SigninAdminRequest request, HttpSession session
    ) {
        return ResponseEntity.ok(adminService.signin(request, session));
    }

    // 관리자 로그아웃 요청 API
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        adminService.logout(session);
        return ResponseEntity.ok().build();
    }

    /**
     * 관리자 상세 정보 조회 API
     */
    @GetMapping("/{adminId}")
    public ResponseEntity<GetAdminDetailResponse> getAdmin(@PathVariable Long adminId) {
        GetAdminDetailResponse response = adminService.getAdmin(adminId);
        return ResponseEntity.ok(response);
    }

    /**
     * 관리자 정보 수정 API
     */
    @PatchMapping("/{adminId}")
    public ResponseEntity<UpdateAdminResponse> updateAdmin(
            @Valid @RequestBody UpdateAdminRequest request,
            @PathVariable Long adminId
    ) {
        UpdateAdminResponse response = adminService.updateInfo(request, adminId);
        return ResponseEntity.ok(response);
    }

    /**
     * 관리자 role 변경 API
     */
    @PatchMapping("/{adminId}/roles")
    public ResponseEntity<UpdateAdminRoleResponse>  updateAdminRole(
            @Valid @RequestBody UpdateAdminRoleRequest request,
            @PathVariable Long adminId
            ) {
        UpdateAdminRoleResponse response = adminService.updateRole(request, adminId);
        return ResponseEntity.ok(response);
    }

    /**
     * 관리자 status 변경 API
     */
    @PatchMapping("/{adminId}/status")
    public ResponseEntity<UpdateAdminStatusResponse> updateAdminStatus(
            @Valid @RequestBody UpdateAdminStatusRequest request,
            @PathVariable Long adminId
    ) {
        UpdateAdminStatusResponse response = adminService.updateStatus(request, adminId);
        return ResponseEntity.ok(response);
    }

    /**
     * 관리자 삭제 API
     */
    @DeleteMapping("/{adminId}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long adminId) {
        adminService.deleteAdmin(adminId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{adminId}/approve")
    public ResponseEntity<ApproveAdminResponse> approveAdmin(
            @PathVariable Long adminId,
            @Valid @RequestBody ApproveAdminRequest request
    ) {
        ApproveAdminResponse response = adminService.approveAdmin(adminId, request);
        return ResponseEntity.ok(response);
    }
}
