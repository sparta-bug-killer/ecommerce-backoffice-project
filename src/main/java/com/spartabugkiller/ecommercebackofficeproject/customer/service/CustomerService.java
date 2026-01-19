package com.spartabugkiller.ecommercebackofficeproject.customer.service;

import com.spartabugkiller.ecommercebackofficeproject.customer.dto.request.CustomerRequest;
import com.spartabugkiller.ecommercebackofficeproject.customer.dto.response.CustomerResponse;
import com.spartabugkiller.ecommercebackofficeproject.customer.entity.Customer;
import com.spartabugkiller.ecommercebackofficeproject.customer.entity.CustomerStatus;
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
        return new CustomerResponse(customerRepository.save(customer), 0L, 0L);
    }

    // 검색/필터 없이 단순 조회용
    @Transactional(readOnly = true)
    public Page<CustomerResponse> getCustomers(Pageable pageable) {
        return getCustomers("", CustomerStatus.ACTIVE, pageable);
    }

    // 검색 + 상태 필터용
    @Transactional(readOnly = true)
    public Page<CustomerResponse> getCustomers(String keyword, CustomerStatus status, Pageable pageable) {
        return customerRepository.findByStatusAndUsernameContainingOrStatusAndEmailContaining(
                        status, keyword, status, keyword, pageable)
                .map(customer -> new CustomerResponse(customer, 0L, 0L));
    }

    @Transactional(readOnly = true)
    public CustomerResponse getCustomer(Long customerId) {
        Customer customer = findCustomer(customerId);
        return new CustomerResponse(customer, 0L, 0L);
    }

    @Transactional
    public CustomerResponse updateCustomer(Long customerId, CustomerRequest request) {
        Customer customer = findCustomer(customerId);
        customer.update(request);
        return new CustomerResponse(customer, 0L, 0L);
    }

    @Transactional
    public CustomerResponse updateStatus(Long customerId, String statusStr) {
        Customer customer = findCustomer(customerId);

        CustomerStatus status;
        try {
            status = CustomerStatus.valueOf(statusStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("올바르지 않은 상태값입니다: " + statusStr);
        }

        customer.updateStatus(status);
        return new CustomerResponse(customer, 0L, 0L);
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
