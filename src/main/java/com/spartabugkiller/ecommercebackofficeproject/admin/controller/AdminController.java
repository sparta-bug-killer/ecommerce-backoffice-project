package com.spartabugkiller.ecommercebackofficeproject.admin.controller;

import com.spartabugkiller.ecommercebackofficeproject.admin.dto.response.AdminDetailResponse;
import com.spartabugkiller.ecommercebackofficeproject.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/{adminId}")
    public ResponseEntity<AdminDetailResponse> getAdmin(@PathVariable Long adminId) {
        AdminDetailResponse response = adminService.getAdmin(adminId);
        return ResponseEntity.ok(response);
    }
}
