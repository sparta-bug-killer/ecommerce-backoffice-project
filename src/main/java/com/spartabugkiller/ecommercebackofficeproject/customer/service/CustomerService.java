package com.spartabugkiller.ecommercebackofficeproject.customer.service;

import com.spartabugkiller.ecommercebackofficeproject.customer.dto.request.CustomerRequest;
import com.spartabugkiller.ecommercebackofficeproject.customer.dto.request.CustomerResponse;
import com.spartabugkiller.ecommercebackofficeproject.customer.entity.Customer;
import com.spartabugkiller.ecommercebackofficeproject.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional
    public CustomerResponse signup(CustomerRequest request) {
        Customer customer = new Customer(request);
        return new CustomerResponse(customerRepository.save(customer));
    }

    // 페이징 적용된 목록 조회
    @Transactional(readOnly = true)
    public Page<CustomerResponse> getCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable)
                .map(CustomerResponse::new);
    }

    @Transactional(readOnly = true)
    public CustomerResponse getCustomer(Long customerId) {
        return new CustomerResponse(findCustomer(customerId));
    }

    @Transactional
    public CustomerResponse updateCustomer(Long customerId, CustomerRequest request) {
        Customer customer = findCustomer(customerId);
        customer.update(request);
        return new CustomerResponse(customer);
    }

    @Transactional
    public CustomerResponse updateStatus(Long customerId, String status) {
        Customer customer = findCustomer(customerId);
        customer.updateStatus(status);
        return new CustomerResponse(customer);
    }

    @Transactional
    public void deleteCustomer(Long customerId) {
        Customer customer = findCustomer(customerId);
        customer.softDelete();
    }

    private Customer findCustomer(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("해당 고객이 존재하지 않습니다. ID: " + customerId));
    }
}