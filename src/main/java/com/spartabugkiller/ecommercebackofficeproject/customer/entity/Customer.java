package com.spartabugkiller.ecommercebackofficeproject.customer.entity;

import com.spartabugkiller.ecommercebackofficeproject.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "customers")
public class Customer extends BaseEntity { // global에 있는 BaseEntity 상속

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String phoneNumber;

    @Enumerated(EnumType.STRING) // Enum을 숫자가 아닌 문자열로 DB에 저장
    @Column(nullable = false)
    private CustomerStatus status = CustomerStatus.ACTIVE;

    // 가입 시 사용할 생성자
    public Customer(String name, String email, String password, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.status = CustomerStatus.ACTIVE;
    }
}