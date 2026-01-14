package com.spartabugkiller.ecommercebackofficeproject.customer.controller;

import com.spartabugkiller.ecommercebackofficeproject.customer.dto.request.CustomerRequest;
import com.spartabugkiller.ecommercebackofficeproject.customer.dto.request.CustomerResponse;
import com.spartabugkiller.ecommercebackofficeproject.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    /**
     * 회원가입 API
     */
    @PostMapping("/signup")
    public ResponseEntity<CustomerResponse> signUp(@RequestBody CustomerRequest request) {

        CustomerResponse response = customerService.signUp(request);
        return ResponseEntity.ok(response);
    }
}