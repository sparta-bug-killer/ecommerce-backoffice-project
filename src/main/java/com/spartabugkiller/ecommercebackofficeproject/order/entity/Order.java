package com.spartabugkiller.ecommercebackofficeproject.order.entity;

import com.spartabugkiller.ecommercebackofficeproject.admin.entity.Admin;
import com.spartabugkiller.ecommercebackofficeproject.customer.entity.Customer;
import com.spartabugkiller.ecommercebackofficeproject.global.exception.ErrorCode;
import com.spartabugkiller.ecommercebackofficeproject.order.exception.OrderCancelNotAllowedException;
import com.spartabugkiller.ecommercebackofficeproject.order.exception.OrderCancelReasonRequiredException;
import com.spartabugkiller.ecommercebackofficeproject.order.exception.OrderStatusChangeNotAllowedException;
import com.spartabugkiller.ecommercebackofficeproject.product.entity.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 주문번호 예시 : 20260116-001
    @Column(nullable = false, unique = true, length = 50)
    private String orderNum;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(nullable = false)
    private int totalPrice;

    @Column(nullable = false)
    private int quantity;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    private LocalDateTime confirmedAt;

    private LocalDateTime canceledAt;

    @Column(length = 255)
    private String cancelReason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @Builder
    public Order(String orderNum, OrderStatus status, int totalPrice, int quantity,
                 Customer customer, Product product, Admin admin) {
        this.orderNum = orderNum;
        this.status = status;
        this.totalPrice = totalPrice;
        this.quantity = quantity;
        this.customer = customer;
        this.product = product;
        this.admin = admin;
    }

    // 주문 상태 변경
    public void changeStatus(OrderStatus newStatus) {

        // 준비중 -> 배송중
        if (this.status == OrderStatus.READY && newStatus == OrderStatus.SHIPPING) {
            this.status = newStatus;
            return;
        }

        // 배송중 -> 배송완료
        if (this.status == OrderStatus.SHIPPING && newStatus == OrderStatus.COMPLETED) {
            this.status = newStatus;
            return;
        }
        throw new OrderStatusChangeNotAllowedException(ErrorCode.ORDER_STATUS_CHANGE_NOT_ALLOWED);
    }

    // 주문 취소
    public void cancel(String cancelReason) {

        // 상태 검증
        if (this.status != OrderStatus.READY) {
            throw new OrderCancelNotAllowedException(ErrorCode.ORDER_CANCEL_NOT_ALLOWED);
        }

        // 취소 사유 검증
        if (cancelReason == null || cancelReason.isBlank()) {
            throw new OrderCancelReasonRequiredException(ErrorCode.ORDER_CANCEL_REASON_REQUIRED);
        }

        // 상태 변경
        this.status = OrderStatus.CANCELED;
        // 취소 사유 저장
        this.cancelReason = cancelReason;
        // 취소 시간
        this.canceledAt = LocalDateTime.now();
    }

}
