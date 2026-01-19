package com.spartabugkiller.ecommercebackofficeproject.order.controller;

import com.spartabugkiller.ecommercebackofficeproject.global.common.SessionUtils;
import com.spartabugkiller.ecommercebackofficeproject.global.dto.ApiResponse;
import com.spartabugkiller.ecommercebackofficeproject.order.dto.request.OrderCancelRequest;
import com.spartabugkiller.ecommercebackofficeproject.order.dto.request.OrderCreateRequest;
import com.spartabugkiller.ecommercebackofficeproject.order.dto.request.OrderStatusUpdateRequest;
import com.spartabugkiller.ecommercebackofficeproject.order.dto.response.*;
import com.spartabugkiller.ecommercebackofficeproject.order.entity.OrderStatus;
import com.spartabugkiller.ecommercebackofficeproject.order.service.OrderService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * CS 주문 생성
     */
    @PostMapping("/orders")
    public ResponseEntity<ApiResponse<OrderCreateResponse>> createOrder(
            @Valid @RequestBody OrderCreateRequest request,
            HttpSession session) {

        Long adminId = SessionUtils.getLoginAdmin(session).getId();

        OrderCreateResponse response = orderService.createOrder(request, adminId);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(response));
    }

    /**
     * 주문 상세 조회
     */
    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderDetailResponse> getOrderDetail(
            @PathVariable Long id,
            HttpSession session
    ) {
        SessionUtils.getLoginAdmin(session);

        return ResponseEntity.ok(orderService.getOrderDetail(id));
    }

    /**
     * 주문 리스트 조회
     */
    @GetMapping("/orders")
    public ResponseEntity<ApiResponse<Page<OrderListResponse>>> getOrderList(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) OrderStatus status,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            HttpSession session) {

        SessionUtils.getLoginAdmin(session);

        Page<OrderListResponse> responses = orderService.getOrderList(keyword, status, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.ok(responses));
    }

    /**
     * 주문 상태 조회
     */
    @PatchMapping("orders/{id}/status")
    public ResponseEntity<OrderStatusUpdateResponse> updateOrderStatus(
            @PathVariable Long id,
            @Valid @RequestBody OrderStatusUpdateRequest request,
            HttpSession session
    ) {
        SessionUtils.getLoginAdmin(session);

        return ResponseEntity.ok(orderService.updateOrderStatus(id, request));
    }

    /**
     * 주문 취소
     */
    @PostMapping("/orders/{orderId}/cancel")
    public ResponseEntity<OrderCancelResponse> cancelOrder(
            @PathVariable Long orderId,
            @Valid @RequestBody OrderCancelRequest request,
            HttpSession session
    ) {

        SessionUtils.getLoginAdmin(session);
        return ResponseEntity.ok(orderService.cancelOrder(orderId, request));
    }
}
