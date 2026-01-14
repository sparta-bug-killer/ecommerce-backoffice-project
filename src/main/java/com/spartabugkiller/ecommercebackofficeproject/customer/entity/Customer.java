package com.spartabugkiller.ecommercebackofficeproject.customer.entity;

import com.spartabugkiller.ecommercebackofficeproject.customer.dto.request.CustomerRequest;
import com.spartabugkiller.ecommercebackofficeproject.global.common.BaseEntity; // BaseEntity 위치 확인 필요
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "customers")
public class Customer extends BaseEntity { // 상속 적용

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
    private String status = "ACTIVE";

    public Customer(CustomerRequest request) {
        this.username = request.getUsername();
        this.email = request.getEmail();
        this.password = request.getPassword();
    }

    public void update(CustomerRequest request) {
        this.username = request.getUsername();
        this.email = request.getEmail();
    }

    public void updateStatus(String status) {
        this.status = status;
    }

    // 논리 삭제: BaseEntity의 기능을 활용하거나 status 변경
    public void softDelete() {
        this.status = "DELETED";
    }
}