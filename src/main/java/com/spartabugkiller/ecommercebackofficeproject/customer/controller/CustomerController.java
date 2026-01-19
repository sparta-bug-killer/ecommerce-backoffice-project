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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admins/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    /**
     * 고객 전체 목록 조회 (페이징 및 검색)
     */
    @GetMapping
    public ResponseEntity<ApiResponse<GetCustomersResponse>> getCustomers(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) CustomerStatus status,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String order,
            HttpSession session
    ) {
        // 세션 검증 (팀 표준 스타일)
        SessionUtils.getLoginAdmin(session);

        // 페이지 번호 방어 로직 (AdminController 스타일)
        int pageIndex = Math.max(page - 1, 0);

        GetCustomersResponse response = customerService.getCustomers(keyword, status, pageIndex, size, sortBy, order);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    /**
     * 고객 상세 조회
     */
    @GetMapping("/{customerId}")
    public ResponseEntity<ApiResponse<CustomerResponse>> getCustomer(
            @PathVariable Long customerId,
            HttpSession session
    ) {
        SessionUtils.getLoginAdmin(session);
        CustomerResponse response = customerService.getCustomerDetail(customerId);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    /**
     * 고객 정보 수정 (이름, 이메일, 전화번호)
     */
    @PutMapping("/{customerId}")
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
     * 고객 상태 변경 (활성/휴면/탈퇴 등)
     */
    @PatchMapping("/{customerId}/status")
    public ResponseEntity<ApiResponse<CustomerResponse>> updateCustomerStatus(
            @PathVariable Long customerId,
            @RequestParam String status,
            HttpSession session
    ) {
        SessionUtils.getLoginAdmin(session);

        // 입력받은 문자열을 Enum으로 변환
        CustomerStatus targetStatus = CustomerStatus.valueOf(status.toUpperCase());

        CustomerResponse response = customerService.updateStatus(customerId, targetStatus);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    /**
     * 고객 삭제 (관리자 권한)
     */
    @DeleteMapping("/{customerId}")
    public ResponseEntity<ApiResponse<Void>> deleteCustomer(
            @PathVariable Long customerId,
            HttpSession session
    ) {
        SessionUtils.getLoginAdmin(session);
        customerService.deleteCustomer(customerId);

        // AdminController 146행 스타일 (200 OK와 noContent 응답 조합)
        return ResponseEntity.ok(ApiResponse.noContent());
    }
}