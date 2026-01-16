package com.spartabugkiller.ecommercebackofficeproject.order.repository;

import com.spartabugkiller.ecommercebackofficeproject.order.entity.OrderDailySequence;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderDailySequenceRepository
        extends JpaRepository<OrderDailySequence, String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from OrderDailySequence s where s.orderDate = :date")
    Optional<OrderDailySequence> findByDateForUpdate(
            @Param("date") String date
    );
}