package com.spartabugkiller.ecommercebackofficeproject.customer.controller;

import com.spartabugkiller.ecommercebackofficeproject.customer.dto.request.CustomerRequest;
import com.spartabugkiller.ecommercebackofficeproject.customer.dto.response.CustomerResponse;
import com.spartabugkiller.ecommercebackofficeproject.customer.entity.CustomerStatus;
import com.spartabugkiller.ecommercebackofficeproject.customer.service.CustomerService;
import com.spartabugkiller.ecommercebackofficeproject.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/signup")
    public ApiResponse<CustomerResponse> signup(@RequestBody CustomerRequest request) {
        return ApiResponse.created(customerService.signup(request));
    }

    @GetMapping
    public ApiResponse<Page<CustomerResponse>> getCustomers(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) CustomerStatus status,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        return ApiResponse.ok(customerService.getCustomers(keyword, status, pageable));
    }

    @GetMapping("/{id}")
    public ApiResponse<CustomerResponse> getCustomer(@PathVariable Long id) {
        return ApiResponse.ok(customerService.getCustomerDetail(id));
    }

    @PutMapping("/{id}")
    public ApiResponse<String> updateCustomer(@PathVariable Long id, @RequestBody CustomerRequest request) {
        customerService.updateCustomer(id, request);
        return ApiResponse.ok("정보 수정이 완료되었습니다.");
    }

    @PatchMapping("/{id}/status")
    public ApiResponse<String> updateStatus(@PathVariable Long id, @RequestParam CustomerStatus status) {
        customerService.updateStatus(id, status);
        return ApiResponse.ok("상태 변경이 완료되었습니다.");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ApiResponse.ok("고객 삭제가 완료되었습니다.");
    }
}