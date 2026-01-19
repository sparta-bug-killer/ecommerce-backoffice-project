package com.spartabugkiller.ecommercebackofficeproject.customer.repository;

import com.spartabugkiller.ecommercebackofficeproject.customer.entity.Customer;
import com.spartabugkiller.ecommercebackofficeproject.customer.entity.CustomerStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("""
        SELECT c FROM Customer c
        WHERE (:status IS NULL OR c.status = :status)
        AND (
            :keyword IS NULL
            OR c.username LIKE %:keyword%
            OR c.email LIKE %:keyword%
        )
    """)
    Page<Customer> searchCustomers(
            @Param("status") CustomerStatus status,
            @Param("keyword") String keyword,
            Pageable pageable
    );
}
