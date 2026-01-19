package com.spartabugkiller.ecommercebackofficeproject.customer.entity;

import com.spartabugkiller.ecommercebackofficeproject.customer.dto.request.CustomerRequest;
import com.spartabugkiller.ecommercebackofficeproject.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "customers")
public class Customer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CustomerStatus status = CustomerStatus.ACTIVE;

    public Customer(CustomerRequest request) {
        this.username = request.getUsername();
        this.email = request.getEmail();
        this.phoneNumber = request.getPhoneNumber();
    }

    public void update(CustomerRequest request) {
        this.username = request.getUsername();
        this.email = request.getEmail();
    }

    public void updateStatus(CustomerStatus status) {
        this.status = status;
    }

    public void softDelete() {
        this.status = CustomerStatus.DELETED;
    }
}
