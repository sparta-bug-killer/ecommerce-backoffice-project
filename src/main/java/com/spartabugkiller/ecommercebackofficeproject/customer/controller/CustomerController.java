package com.spartabugkiller.ecommercebackofficeproject.customer.controller;


import com.spartabugkiller.ecommercebackofficeproject.customer.dto.request.CustomerRequest;
import com.spartabugkiller.ecommercebackofficeproject.customer.dto.response.CustomerResponse;
import com.spartabugkiller.ecommercebackofficeproject.customer.dto.response.GetCustomersResponse;
import com.spartabugkiller.ecommercebackofficeproject.customer.entity.CustomerStatus;
import com.spartabugkiller.ecommercebackofficeproject.customer.service.CustomerService;
import com.spartabugkiller.ecommercebackofficeproject.global.common.SessionUtils;
import com.spartabugkiller.ecommercebackofficeproject.global.dto.ApiResponse;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/admins/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    // 1. 고객 전체 목록 조회 (페이징)
    @GetMapping
    public ResponseEntity<ApiResponse<GetCustomersResponse>> getCustomers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) CustomerStatus status,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String order
    ) {
        // 서비스의 파라미터 6개 순서에 맞춰서 호출 (page는 0부터 시작하므로 -1)
        GetCustomersResponse response = customerService.getCustomers(keyword, status, page - 1, size, sortBy, order);

        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    // 2. 고객 상세 조회
    @GetMapping("/{customerId}")
    public ResponseEntity<ApiResponse<CustomerResponse>> getCustomer(
            @PathVariable Long customerId,
            HttpSession session) {
        SessionUtils.validateAdmin(session);
        CustomerResponse customer = customerService.getCustomerDetail(customerId);
        return ResponseEntity.ok(ApiResponse.ok(customer));
    }

    // 3. 고객 정보 수정 (이름, 이메일 등)
    @PutMapping("/{customerId}")
    public ResponseEntity<ApiResponse<CustomerResponse>> updateCustomer(
            @PathVariable Long customerId,
            @Valid @RequestBody CustomerRequest requestDto,
            HttpSession session) {
        SessionUtils.validateAdmin(session);
        CustomerResponse updatedCustomer = customerService.updateCustomer(customerId, requestDto);
        return ResponseEntity.ok(ApiResponse.ok(updatedCustomer));
    }

    // 4. 고객 상태 변경
    @PatchMapping("/{customerId}/status")
    public ResponseEntity<ApiResponse<String>> updateCustomerStatus(
            @PathVariable Long customerId,
            @RequestParam String status,
            HttpSession session) {
        SessionUtils.validateAdmin(session);

        // 대소문자 무관하게 변환
        CustomerStatus targetStatus = CustomerStatus.valueOf(status.toUpperCase());

        customerService.updateStatus(customerId, targetStatus);
        return ResponseEntity.ok(ApiResponse.ok("고객 상태가 성공적으로 변경되었습니다."));
    }

    // 5. 고객 삭제
    @DeleteMapping("/{customerId}")
    public ResponseEntity<ApiResponse<String>> deleteCustomer(
            @PathVariable Long customerId,
            HttpSession session) {
        SessionUtils.validateAdmin(session);
        customerService.deleteCustomer(customerId);
        return ResponseEntity.noContent().build();
    }
}