package com.spartabugkiller.ecommercebackofficeproject.order.dto.response;

import com.spartabugkiller.ecommercebackofficeproject.order.entity.Order;
import com.spartabugkiller.ecommercebackofficeproject.order.entity.OrderStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderCreateResponse {

    private final Long id;
    private final String orderNum;
    private final String customerName;
    private final String productName;
    private final int quantity;
    private final int totalPrice;
    private final OrderStatus status;
    private final String adminName;
    private final LocalDateTime createdAt;

    public OrderCreateResponse(Order order) {
        this.id = order.getId();
        this.orderNum = order.getOrderNum();
        this.customerName = order.getCustomer().getUsername();
        this.productName = order.getProduct().getName();
        this.quantity = order.getQuantity();
        this.totalPrice = order.getTotalPrice();
        this.status = order.getStatus();
        this.adminName = order.getAdmin() != null ? order.getAdmin().getName() : "고객 직접 주문";
        this.createdAt = order.getCreatedAt();
    }
}
