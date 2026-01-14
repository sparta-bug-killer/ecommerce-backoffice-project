package com.spartabugkiller.ecommercebackofficeproject.customer.service;

import com.spartabugkiller.ecommercebackofficeproject.customer.dto.CustomerResponseDto;
import com.spartabugkiller.ecommercebackofficeproject.customer.entity.Customer;
import com.spartabugkiller.ecommercebackofficeproject.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerService {

    private final CustomerRepository customerRepository;

    /**
     * 전체 조회 로직
     */
    public List<CustomerResponseDto> getCustomers() {
        return customerRepository.findAll().stream()
                .map(CustomerResponseDto::new)
                .toList();
    }

    /**
     * 상세 조회 로직
     */
    public CustomerResponseDto getCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("해당 고객이 존재하지 않습니다. ID: " + customerId));
        return new CustomerResponseDto(customer);
    }
}