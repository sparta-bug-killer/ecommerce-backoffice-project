package com.spartabugkiller.ecommercebackofficeproject.order.controller;

import com.spartabugkiller.ecommercebackofficeproject.global.common.SessionUtils;
import com.spartabugkiller.ecommercebackofficeproject.order.dto.request.OrderCreateRequest;
import com.spartabugkiller.ecommercebackofficeproject.order.dto.response.OrderCreateResponse;
import com.spartabugkiller.ecommercebackofficeproject.order.dto.response.OrderDetailResponse;
import com.spartabugkiller.ecommercebackofficeproject.order.service.OrderService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     *  주문 생성
     */
    @PostMapping("/orders")
    public ResponseEntity<OrderCreateResponse> createOrder(
            @Valid @RequestBody OrderCreateRequest request,
            HttpSession session) {

        Long adminId = SessionUtils.getLoginAdmin(session).getId();

        OrderCreateResponse response = orderService.createOrder(request, adminId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 주문 상세 조회
     */
    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderDetailResponse> getOrderDetail(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(orderService.getOrderDetail(id));
    }
}
