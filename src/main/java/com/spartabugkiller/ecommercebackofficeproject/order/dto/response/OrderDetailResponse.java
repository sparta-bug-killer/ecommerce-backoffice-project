package com.spartabugkiller.ecommercebackofficeproject.order.dto.response;

import com.spartabugkiller.ecommercebackofficeproject.order.entity.Order;
import com.spartabugkiller.ecommercebackofficeproject.order.entity.OrderStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderDetailResponse {

    private String orderNum;
    private String customerName;
    private String customerEmail;
    private String productName;
    private int quantity;
    private int totalPrice;

    private LocalDateTime orderDate;
    private OrderStatus status;

    // 관리자 정보 (직접주문이면 필요없음)
    private String adminName;
    private String adminEmail;
    private String adminRole;

    public OrderDetailResponse(Order order) {
        this.orderNum = order.getOrderNum();
        this.customerName = order.getCustomer().getUsername();
        this.customerEmail = order.getCustomer().getEmail();
        this.productName = order.getProduct().getName();
        this.quantity = order.getQuantity();
        this.totalPrice = order.getTotalPrice();
        this.orderDate = order.getCreatedAt();
        this.status = order.getStatus();

        // CS 주문일 경우 이름/이메일/역할 넣기
        if (order.getAdmin() != null) {
            this.adminName = order.getAdmin().getName();
            this.adminEmail = order.getAdmin().getEmail();
            this.adminRole = order.getAdmin().getRole().name();
        }
    }
}
