package com.spartabugkiller.ecommercebackofficeproject.order.dto.response;

import com.spartabugkiller.ecommercebackofficeproject.order.entity.Order;
import com.spartabugkiller.ecommercebackofficeproject.order.entity.OrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class OrderCancelResponse {

    private Long orderId;
    private String orderNum;

    private OrderStatus beforeStatus;
    private OrderStatus afterStatus;

    private String cancelReason;
    private LocalDateTime canceledAt;

    public static OrderCancelResponse from(Order order, OrderStatus beforeStatus) {
        return OrderCancelResponse.builder()
                .orderId(order.getId())
                .orderNum(order.getOrderNum())
                .beforeStatus(beforeStatus)
                .afterStatus(order.getStatus())
                .cancelReason(order.getCancelReason())
                .canceledAt(order.getCanceledAt())
                .build();
    }
}
