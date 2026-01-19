package com.spartabugkiller.ecommercebackofficeproject.order.dto.response;

import com.spartabugkiller.ecommercebackofficeproject.order.entity.Order;
import com.spartabugkiller.ecommercebackofficeproject.order.entity.OrderStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderListResponse {

    private final Long id;
    private final String orderNum;
    private final String username;
    private final String name;
    private final int quantity;
    private final int price;
    private final LocalDateTime createdAt;
    private final OrderStatus status;
    private final String adminName;

    public OrderListResponse(Order order) {
        this.id = order.getId();
        this.orderNum = order.getOrderNum();
        this.username = order.getCustomer().getUsername();
        this.name = order.getProduct().getName();
        this.quantity = order.getQuantity();
        this.price = order.getProduct().getPrice();
        this.createdAt = order.getCreatedAt();
        this.status = order.getStatus();
        this.adminName = order.getAdmin() != null ? order.getAdmin().getName() : "고객 주문";
    }
}
