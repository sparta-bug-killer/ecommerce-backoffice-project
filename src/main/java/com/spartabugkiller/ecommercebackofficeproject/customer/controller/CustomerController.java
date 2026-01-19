package com.spartabugkiller.ecommercebackofficeproject.customer.controller;

import com.spartabugkiller.ecommercebackofficeproject.customer.dto.request.CustomerRequest;
import com.spartabugkiller.ecommercebackofficeproject.customer.dto.response.CustomerResponse;
import com.spartabugkiller.ecommercebackofficeproject.customer.dto.response.GetCustomersResponse;
import com.spartabugkiller.ecommercebackofficeproject.customer.entity.CustomerStatus;
import com.spartabugkiller.ecommercebackofficeproject.customer.service.CustomerService;
import com.spartabugkiller.ecommercebackofficeproject.global.dto.ApiResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers") // 팀 규격에 따라 /api/admins/customers로 바꿀 수도 있지만 일단 유지합니다.
public class CustomerController {

    private final CustomerService customerService;

    /**
     * 고객 리스트 조회 API (검색, 페이징, 집계 포함)
     */
    @GetMapping
    public ResponseEntity<ApiResponse<GetCustomersResponse>> getCustomers(
            HttpSession session,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) CustomerStatus status,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String order
    ) {
        int pageIndex = Math.max(page - 1, 0);
        GetCustomersResponse response = customerService.getCustomers(session, keyword, status, pageIndex, size, sortBy, order);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    /**
     * 고객 상세 조회 API
     */
    @GetMapping("/{customerId}")
    public ResponseEntity<ApiResponse<CustomerResponse>> getCustomer(
            @PathVariable Long customerId,
            HttpSession session
    ) {
        CustomerResponse response = customerService.getCustomerDetail(customerId, session);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    /**
     * 고객 정보 수정 API
     */
    @PatchMapping("/{customerId}")
    public ResponseEntity<ApiResponse<CustomerResponse>> updateCustomer(
            @PathVariable Long customerId,
            @Valid @RequestBody CustomerRequest request,
            HttpSession session
    ) {
        CustomerResponse response = customerService.updateCustomer(customerId, request, session);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    /**
     * 고객 상태 변경 API
     */
    @PatchMapping("/{customerId}/status")
    public ResponseEntity<ApiResponse<CustomerResponse>> updateStatus(
            @PathVariable Long customerId,
            @RequestParam CustomerStatus status,
            HttpSession session
    ) {
        CustomerResponse response = customerService.updateStatus(customerId, status, session);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    /**
     * 고객 삭제(탈퇴) API
     */
    @DeleteMapping("/{customerId}")
    public ResponseEntity<ApiResponse<Void>> deleteCustomer(
            @PathVariable Long customerId,
            HttpSession session
    ) {
        customerService.deleteCustomer(customerId, session);
        return ResponseEntity.ok(ApiResponse.noContent());
    }
}