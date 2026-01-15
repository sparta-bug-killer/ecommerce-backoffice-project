package com.spartabugkiller.ecommercebackofficeproject.admin.entity;


import com.spartabugkiller.ecommercebackofficeproject.admin.dto.request.UpdateAdminRequest;
import com.spartabugkiller.ecommercebackofficeproject.admin.dto.request.UpdateAdminRequest;
import com.spartabugkiller.ecommercebackofficeproject.admin.dto.request.UpdateAdminRoleRequest;
import com.spartabugkiller.ecommercebackofficeproject.admin.dto.request.UpdateAdminStatusRequest;

import com.spartabugkiller.ecommercebackofficeproject.admin.exception.AdminInactiveException;

import com.spartabugkiller.ecommercebackofficeproject.global.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "admins")
@NoArgsConstructor
public class Admin extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private AdminRole role;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private AdminStatus status;
    private Long approvedBy;
    private LocalDateTime approvedAt;
    private LocalDateTime rejectedAt;
    private String rejectedReason;
    private LocalDateTime deletedAt;

    // 신규 관리자 생성 (무조건 PENDING)
    public Admin(String name, String email, String password, String phoneNumber, AdminRole role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.status = AdminStatus.PENDING;
    }

    // 승인 처리 (APPROVED)
    public void approve(Long superAdminId) {
        this.status = AdminStatus.APPROVED;
        this.approvedBy = superAdminId;
        this.approvedAt = LocalDateTime.now();
    }

    // 거절 처리 (REJECTED)
    public void reject(String reason) {
        this.status = AdminStatus.REJECTED;
        this.rejectedAt = LocalDateTime.now();
        this.rejectedReason = reason;
    }

    // 계정 비활성화 (INACTIVE)
    public void deactivate() {
        if (this.deletedAt != null) {
            throw new AdminInactiveException();
        }
        this.status = AdminStatus.INACTIVE;
    }

    // Soft Delete
    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }

    public void updateInfo(UpdateAdminRequest request) {
        this.name = request.getName();
        this.email = request.getEmail();
        this.phoneNumber = request.getPhoneNumber();
    }

    public void updateRole(UpdateAdminRoleRequest request) {
        this.role = request.getRole() == null ? this.role : request.getRole();
    }

    public void updateStatus(@Valid UpdateAdminStatusRequest request) {
        this.status = request.getStatus() == null ? this.status : request.getStatus();
    }


    public void updatePassword(String encodedPassword) {
        this.password = encodedPassword;
    }

}
