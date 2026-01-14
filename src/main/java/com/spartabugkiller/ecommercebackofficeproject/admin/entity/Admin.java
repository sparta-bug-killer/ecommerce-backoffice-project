package com.spartabugkiller.ecommercebackofficeproject.admin.entity;

import com.spartabugkiller.ecommercebackofficeproject.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.property.access.internal.PropertyAccessStrategyIndexBackRefImpl;

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AdminRole role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AdminStatus status;

    // 승인 정보
    private Long approvedBy;
    private LocalDateTime approvedAt;

    // 거절 정보
    private LocalDateTime rejectedAt;
    private String rejectedReason;

    // 정지 정보
    private LocalDateTime blockedAt;
    private String blockedReason;

    // 신규 관리자 생성 (무조건 PENDING)
    public Admin(String name, String email, String password, String phoneNumber, AdminRole role) {

        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.status = AdminStatus.PENDING;
    }

    // 승인 처리
    public void approve(Long superAdminId) {
        this.status = AdminStatus.APPROVED;
        this.approvedBy = superAdminId;
        this.approvedAt = LocalDateTime.now();
    }

    // 거절 처리
    public void reject(String reason) {
        this.status = AdminStatus.REJECTED;
        this.rejectedAt = LocalDateTime.now();
        this.rejectedReason = reason;
    }

    // 계정 정지
    public void blocked(String reason) {
        this.status = AdminStatus.BLOCKED;
        this.blockedAt = LocalDateTime.now();
        this.blockedReason = reason;
    }

    // 정지 해제 (재승인)
    public void unblock() {
        this.status = AdminStatus.APPROVED;
        this.blockedAt = null;
        this.blockedReason = null;
    }

    // 계정 비활성화 (Soft Delete)
    public void deactivate() {
        delete();
    }
}
