package com.spartabugkiller.ecommercebackofficeproject.customer.repository;

import com.spartabugkiller.ecommercebackofficeproject.customer.entity.Customer;
import com.spartabugkiller.ecommercebackofficeproject.customer.entity.CustomerStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c, COUNT(o), SUM(o.totalPrice) " +
            "FROM Customer c LEFT JOIN Order o ON o.customer = c " +
            "WHERE (:keyword IS NULL OR c.username LIKE %:keyword% OR c.email LIKE %:keyword%) " +
            "AND (:status IS NULL OR c.status = :status) " +
            "GROUP BY c")
    Page<Object[]> findAllWithOrderStats(@Param("keyword") String keyword,
                                         @Param("status") CustomerStatus status,
                                         Pageable pageable);

    @Query("SELECT c, COUNT(o), SUM(o.totalPrice) " +
            "FROM Customer c LEFT JOIN Order o ON o.customer = c " +
            "WHERE c.id = :id " +
            "GROUP BY c")
    Optional<Object[]> findDetailWithOrderStats(@Param("id") Long id);
}