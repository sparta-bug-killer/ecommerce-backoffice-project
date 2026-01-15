package com.spartabugkiller.ecommercebackofficeproject.admin.controller;

import com.spartabugkiller.ecommercebackofficeproject.admin.dto.request.SignupAdminRequest;
import com.spartabugkiller.ecommercebackofficeproject.admin.dto.response.SignupAdminResponse;
import com.spartabugkiller.ecommercebackofficeproject.admin.dto.request.UpdateAdminRoleRequest;
import com.spartabugkiller.ecommercebackofficeproject.admin.dto.request.UpdateAdminRequest;
import com.spartabugkiller.ecommercebackofficeproject.admin.dto.request.UpdateAdminStatusRequest;
import com.spartabugkiller.ecommercebackofficeproject.admin.dto.response.GetAdminDetailResponse;
import com.spartabugkiller.ecommercebackofficeproject.admin.dto.response.UpdateAdminResponse;
import com.spartabugkiller.ecommercebackofficeproject.admin.dto.response.UpdateAdminRoleResponse;
import com.spartabugkiller.ecommercebackofficeproject.admin.dto.response.UpdateAdminStatusResponse;
import com.spartabugkiller.ecommercebackofficeproject.admin.service.AdminService;
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
}
