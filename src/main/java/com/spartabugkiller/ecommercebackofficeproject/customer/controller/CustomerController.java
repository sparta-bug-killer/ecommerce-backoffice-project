package com.spartabugkiller.ecommercebackofficeproject.customer.controller;

import com.spartabugkiller.ecommercebackofficeproject.customer.dto.request.CustomerRequest;
import com.spartabugkiller.ecommercebackofficeproject.customer.dto.response.CustomerResponse;
import com.spartabugkiller.ecommercebackofficeproject.customer.entity.CustomerStatus;
import com.spartabugkiller.ecommercebackofficeproject.customer.service.CustomerService;
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

    /**
     * 고객 생성
     */
    @PostMapping("/signup")
    public ResponseEntity<CustomerResponse> signup(
            @Valid @RequestBody CustomerRequest request
    ) {
        return ResponseEntity.ok(customerService.signup(request));
    }

    /**
     * 고객 리스트 조회
     */
    @GetMapping
    public ResponseEntity<Page<CustomerResponse>> getCustomers(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) CustomerStatus status,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                customerService.getCustomers(keyword, status, pageable)
        );
    }

    /**
     * 고객 상세 조회
     */
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> getCustomer(
            @PathVariable Long customerId
    ) {
        return ResponseEntity.ok(customerService.getCustomer(customerId));
    }

    /**
     * 고객 정보 수정
     */
    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> updateCustomer(
            @PathVariable Long customerId,
            @Valid @RequestBody CustomerRequest request
    ) {
        return ResponseEntity.ok(
                customerService.updateCustomer(customerId, request)
        );
    }

    /**
     * 고객 상태 변경
     */
    @PatchMapping("/{customerId}/status")
    public ResponseEntity<CustomerResponse> updateStatus(
            @PathVariable Long customerId,
            @RequestParam CustomerStatus status
    ) {
        return ResponseEntity.ok(
                customerService.updateStatus(customerId, status)
        );
    }

    /**
     * 고객 삭제 (논리 삭제)
     */
    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomer(
            @PathVariable Long customerId
    ) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.noContent().build();
    }
}
