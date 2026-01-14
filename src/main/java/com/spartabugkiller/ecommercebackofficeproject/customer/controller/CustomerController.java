package com.spartabugkiller.ecommercebackofficeproject.customer.controller;

import com.spartabugkiller.ecommercebackofficeproject.customer.dto.request.CustomerRequest;
import com.spartabugkiller.ecommercebackofficeproject.customer.dto.request.CustomerResponse;
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

    @PostMapping("/signup")
    public ResponseEntity<CustomerResponse> signup(@Valid @RequestBody CustomerRequest request) {
        return ResponseEntity.ok(customerService.signup(request));
    }

    // 에러 해결 지점: Pageable 인자 추가
    @GetMapping
    public ResponseEntity<Page<CustomerResponse>> getCustomers(Pageable pageable) {
        return ResponseEntity.ok(customerService.getCustomers(pageable));
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(customerService.getCustomer(customerId));
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable Long customerId, @Valid @RequestBody CustomerRequest request) {
        return ResponseEntity.ok(customerService.updateCustomer(customerId, request));
    }

    @PatchMapping("/{customerId}/status")
    public ResponseEntity<CustomerResponse> updateStatus(@PathVariable Long customerId, @RequestParam String status) {
        return ResponseEntity.ok(customerService.updateStatus(customerId, status));
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.ok("성공적으로 삭제 처리되었습니다.");
    }
}