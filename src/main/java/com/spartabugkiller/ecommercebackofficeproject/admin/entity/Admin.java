package com.spartabugkiller.ecommercebackofficeproject.admin.entity;

import com.spartabugkiller.ecommercebackofficeproject.admin.dto.request.UpdateAdminRequest;
import com.spartabugkiller.ecommercebackofficeproject.admin.dto.request.UpdateAdminRoleRequest;
import com.spartabugkiller.ecommercebackofficeproject.global.common.BaseEntity;
import jakarta.persistence.*;
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

    public void updateInfo(UpdateAdminRequest request) {
        this.name = request.getName() == null ? this.name : request.getName();
        this.email = request.getEmail() == null ? this.email : request.getEmail();
        this.phoneNumber = request.getPhoneNumber() == null ? this.phoneNumber : request.getPhoneNumber();
    }

    public void updateRole(UpdateAdminRoleRequest request) {
        this.role = request.getRole() == null ? this.role : request.getRole();
    }
}
