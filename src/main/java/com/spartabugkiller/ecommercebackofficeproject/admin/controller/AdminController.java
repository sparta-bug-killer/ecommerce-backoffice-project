package com.spartabugkiller.ecommercebackofficeproject.admin.controller;

import com.spartabugkiller.ecommercebackofficeproject.admin.dto.request.SignupAdminRequest;
import com.spartabugkiller.ecommercebackofficeproject.admin.dto.response.AdminDetailResponse;
import com.spartabugkiller.ecommercebackofficeproject.admin.dto.response.SignupAdminResponse;
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
        UpdateAdminResponse response = adminService.update(request, adminId);
        return ResponseEntity.ok(response);
    }
}
