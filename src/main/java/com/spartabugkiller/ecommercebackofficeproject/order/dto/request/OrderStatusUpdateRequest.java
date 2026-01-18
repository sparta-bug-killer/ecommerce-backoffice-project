package com.spartabugkiller.ecommercebackofficeproject.order.dto.request;

import com.spartabugkiller.ecommercebackofficeproject.order.entity.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderStatusUpdateRequest {

    @NotNull(message = "상태 입력은 필수입니다.")
    private OrderStatus status;
}
