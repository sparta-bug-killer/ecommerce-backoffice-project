package com.spartabugkiller.ecommercebackofficeproject.admin.controller;

import com.spartabugkiller.ecommercebackofficeproject.admin.dto.SessionAdmin;
import com.spartabugkiller.ecommercebackofficeproject.admin.dto.request.SigninAdminRequest;
import com.spartabugkiller.ecommercebackofficeproject.admin.dto.request.SignupAdminRequest;
import com.spartabugkiller.ecommercebackofficeproject.admin.dto.response.SigninAdminResponse;
import com.spartabugkiller.ecommercebackofficeproject.admin.dto.response.SignupAdminResponse;
import com.spartabugkiller.ecommercebackofficeproject.admin.service.AdminService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/admins/signup")
    public ResponseEntity<SignupAdminResponse> signup(
            @Valid @RequestBody SignupAdminRequest request
    ) {
        SignupAdminResponse response = adminService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/admins/signin")
    public ResponseEntity<SigninAdminResponse> signin(
            @Valid @RequestBody SigninAdminRequest request, HttpSession session
    ) {
        return ResponseEntity.ok(adminService.signin(request, session));
    }

    @PostMapping("/admins/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        adminService.logout(session);
        return ResponseEntity.ok().build();
    }
}
