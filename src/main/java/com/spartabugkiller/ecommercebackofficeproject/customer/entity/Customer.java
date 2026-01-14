package com.spartabugkiller.ecommercebackofficeproject.customer.entity;

import com.spartabugkiller.ecommercebackofficeproject.customer.dto.request.CustomerRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String status = "ACTIVE"; // 기본 상태: ACTIVE

    @Column(nullable = false)
    private Boolean isDeleted = false;

    // 회원가입 생성자
    public Customer(CustomerRequest request) {
        this.username = request.getUsername();
        this.email = request.getEmail();
        this.password = request.getPassword();
        this.isDeleted = false;
        this.status = "ACTIVE";
    }

    // 정보 수정 메서드
    public void update(CustomerRequest request) {
        this.username = request.getUsername();
        this.email = request.getEmail();
    }

    // 상태 변경 메서드
    public void updateStatus(String status) {
        this.status = status;
    }

    // 논리 삭제 메서드
    public void softDelete() {
        this.isDeleted = true;
        this.status = "DELETED";
    }
}