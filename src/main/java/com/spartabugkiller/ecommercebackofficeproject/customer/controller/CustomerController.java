package com.spartabugkiller.ecommercebackofficeproject.customer.controller;

import com.spartabugkiller.ecommercebackofficeproject.customer.dto.request.CustomerRequest;
import com.spartabugkiller.ecommercebackofficeproject.customer.dto.response.CustomerResponse;
import com.spartabugkiller.ecommercebackofficeproject.customer.entity.CustomerStatus;
import com.spartabugkiller.ecommercebackofficeproject.customer.service.CustomerService;
import com.spartabugkiller.ecommercebackofficeproject.global.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<CustomerResponse>> signup(@Valid @RequestBody CustomerRequest request) {
        CustomerResponse response = customerService.signup(request);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    // 고객 전체 조회 (검색/상태 필터 옵션)
    @GetMapping
    public ResponseEntity<ApiResponse<Page<CustomerResponse>>> getCustomers(
            @RequestParam(required = false, defaultValue = "") String keyword,
            @RequestParam(required = false) CustomerStatus status,
            Pageable pageable
    ) {
        CustomerStatus customerStatus = (status != null) ? status : CustomerStatus.ACTIVE;
        Page<CustomerResponse> response = customerService.getCustomers(keyword, customerStatus, pageable);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    // 단건 조회
    @GetMapping("/{customerId}")
    public ResponseEntity<ApiResponse<CustomerResponse>> getCustomer(@PathVariable Long customerId) {
        CustomerResponse response = customerService.getCustomer(customerId);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    // 고객 정보 수정
    @PutMapping("/{customerId}")
    public ResponseEntity<ApiResponse<CustomerResponse>> updateCustomer(
            @PathVariable Long customerId,
            @Valid @RequestBody CustomerRequest request
    ) {
        CustomerResponse response = customerService.updateCustomer(customerId, request);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    // 고객 상태 변경
    @PatchMapping("/{customerId}/status")
    public ResponseEntity<ApiResponse<CustomerResponse>> updateStatus(
            @PathVariable Long customerId,
            @RequestParam String status
    ) {
        CustomerResponse response = customerService.updateStatus(customerId, status);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    // 논리 삭제
    @DeleteMapping("/{customerId}")
    public ResponseEntity<ApiResponse<Void>> deleteCustomer(@PathVariable Long customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.ok(ApiResponse.noContent());
    }
}
