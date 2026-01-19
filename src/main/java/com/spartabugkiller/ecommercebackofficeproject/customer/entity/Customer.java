package com.spartabugkiller.ecommercebackofficeproject.customer.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "customers")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(name = "phone_number", nullable = false)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CustomerStatus status = CustomerStatus.ACTIVE;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    public Customer(String username, String email, String phone) {
        this.username = username;
        this.email = email;
        this.phone = phone;
    }

    public void updateInfo(String username, String email, String phone) {
        this.username = username;
        this.email = email;
        this.phone = phone;
    }

    public void updateStatus(CustomerStatus status) {
        this.status = status;
    }
}