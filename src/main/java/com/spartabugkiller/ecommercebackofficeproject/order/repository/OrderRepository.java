package com.spartabugkiller.ecommercebackofficeproject.order.repository;

import com.spartabugkiller.ecommercebackofficeproject.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
