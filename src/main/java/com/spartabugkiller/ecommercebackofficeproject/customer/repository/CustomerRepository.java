package com.spartabugkiller.ecommercebackofficeproject.customer.repository;

import com.spartabugkiller.ecommercebackofficeproject.customer.entity.Customer;
import com.spartabugkiller.ecommercebackofficeproject.customer.entity.CustomerStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // 검색 + 상태 필터용
    Page<Customer> findByStatusAndUsernameContainingOrStatusAndEmailContaining(
            CustomerStatus status1, String usernameKeyword,
            CustomerStatus status2, String emailKeyword,
            Pageable pageable
    );
}
