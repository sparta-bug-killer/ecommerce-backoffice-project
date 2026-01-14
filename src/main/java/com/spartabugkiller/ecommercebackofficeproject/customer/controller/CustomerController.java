package com.spartabugkiller.ecommercebackofficeproject.customer.controller;

import com.spartabugkiller.ecommercebackofficeproject.customer.dto.CustomerResponseDto;
import com.spartabugkiller.ecommercebackofficeproject.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    /**
     * 고객 전체 목록 조회
     */
    @GetMapping
    public ResponseEntity<List<CustomerResponseDto>> getCustomers() {
        List<CustomerResponseDto> responseDtos = customerService.getCustomers();
        return ResponseEntity.ok(responseDtos);
    }

    /**
     * 특정 고객 상세 조회
     */
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponseDto> getCustomer(@PathVariable Long customerId) {
        CustomerResponseDto responseDto = customerService.getCustomer(customerId);
        return ResponseEntity.ok(responseDto);
    }
}