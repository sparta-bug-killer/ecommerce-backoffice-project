package com.spartabugkiller.ecommercebackofficeproject.order.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_daily_sequence")
@Getter
@NoArgsConstructor
public class OrderDailySequence {

    @Id
    @Column(name = "order_date", length = 8)
    private String orderDate;

    @Column(nullable = false)
    private int seq;

    public OrderDailySequence(String orderDate) {
        this.orderDate = orderDate;
        this.seq = 0;
    }

    public int nextSeq() {
        this.seq++;
        return this.seq;
    }
}
