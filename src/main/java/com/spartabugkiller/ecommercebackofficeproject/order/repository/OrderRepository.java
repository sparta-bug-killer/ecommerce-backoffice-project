package com.spartabugkiller.ecommercebackofficeproject.order.repository;

import com.spartabugkiller.ecommercebackofficeproject.order.entity.Order;
import com.spartabugkiller.ecommercebackofficeproject.order.entity.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("""
                SELECT o FROM Order o
                JOIN o.customer c
                JOIN o.product p
                LEFT JOIN o.admin a
                WHERE (:status IS NULL OR o.status = :status)
                AND (:keyword IS NULL OR o.orderNum LIKE %:keyword% OR c.username LIKE %:keyword%)
            """)
    Page<Order> findWithFilters(
            @Param("keyword") String keyword,
            @Param("status") OrderStatus status,
            Pageable pageable);
}
