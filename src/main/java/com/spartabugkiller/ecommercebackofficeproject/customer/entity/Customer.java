package com.spartabugkiller.ecommercebackofficeproject.customer.entity;

import com.spartabugkiller.ecommercebackofficeproject.customer.dto.request.CustomerRequest;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CustomerStatus status;

    @Builder.Default
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @Builder.Default
    @Column(nullable = false)
    private String password = "default_password123";

    // [추가] 휴대폰 번호 에러 해결용 필드
    @Builder.Default
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber = "010-0000-0000";

    // 회원가입용 생성자
    public Customer(String name, String email) {
        this.name = name;
        this.email = email;
        this.status = CustomerStatus.ACTIVE;
        this.isDeleted = false;
        this.password = "default_password123";
        this.phoneNumber = "010-0000-0000"; // 기본값 채우기
    }

    public void update(CustomerRequest request) {
        this.name = request.getName();
        this.email = request.getEmail();
    }

    public void setStatus(CustomerStatus status) {
        this.status = status;
    }
}