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

    // 에러 해결: 클래스 중괄호 안으로 들어왔습니다.
    @Column(nullable = false)
    private Boolean isDeleted = false;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    // 회원가입 생성자
    public Customer(CustomerRequest request) {
        this.username = request.getUsername();
        this.email = request.getEmail();
        this.password = request.getPassword();
        this.isDeleted = false; // 저장 시 기본값 강제 주입
    }
}