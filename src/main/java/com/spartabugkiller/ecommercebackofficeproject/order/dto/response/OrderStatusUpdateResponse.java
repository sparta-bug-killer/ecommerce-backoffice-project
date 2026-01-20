package com.spartabugkiller.ecommercebackofficeproject.order.dto.response;

import com.spartabugkiller.ecommercebackofficeproject.order.entity.Order;
import com.spartabugkiller.ecommercebackofficeproject.order.entity.OrderStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderStatusUpdateResponse {

    private Long orderId;
    private String orderNum;

    private OrderStatus beforeStatus;
    private OrderStatus afterStatus;

    public static OrderStatusUpdateResponse from(Order order, OrderStatus beforeStatus) {
        return OrderStatusUpdateResponse.builder()
                .orderId(order.getId())
                .orderNum(order.getOrderNum())
                .beforeStatus(beforeStatus)
                .afterStatus(order.getStatus())
                .build();
    }
}
