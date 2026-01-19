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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins") // ProductController와 동일하게 설정
public class CustomerController {

    private final CustomerService customerService;

    /**
     * 고객 리스트 조회 API
     */
    @GetMapping("/customers")
    public ResponseEntity<ApiResponse<GetCustomersResponse>> getCustomers(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) CustomerStatus status,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String order,
            HttpSession session
    ) {
        // ProductController 스타일: getLoginAdmin으로 세션 검증
        SessionUtils.getLoginAdmin(session);

        int pageIndex = Math.max(page - 1, 0);
        GetCustomersResponse response = customerService.getCustomers(keyword, status, pageIndex, size, sortBy, order);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    /**
     * 고객 상세 조회 API
     */
    @GetMapping("/customers/{customerId}")
    public ResponseEntity<ApiResponse<CustomerResponse>> getCustomer(
            @PathVariable Long customerId,
            HttpSession session
    ) {
        // ProductController 스타일 세션 검증
        SessionUtils.getLoginAdmin(session);

        CustomerResponse response = customerService.getCustomerDetail(customerId);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    /**
     * 고객 정보 수정 API
     */
    @PatchMapping("/customers/{customerId}")
    public ResponseEntity<ApiResponse<CustomerResponse>> updateCustomer(
            @PathVariable Long customerId,
            @Valid @RequestBody CustomerRequest request,
            HttpSession session
    ) {
        SessionUtils.getLoginAdmin(session);

        CustomerResponse response = customerService.updateCustomer(customerId, request);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    /**
     * 고객 삭제 API
     */
    @DeleteMapping("/customers/{customerId}")
    public ResponseEntity<ApiResponse<Void>> deleteCustomer(
            @PathVariable Long customerId,
            HttpSession session
    ) {
        SessionUtils.getLoginAdmin(session);

        customerService.deleteCustomer(customerId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.noContent());
    }
}