package com.spartabugkiller.ecommercebackofficeproject.customer.service;

import com.spartabugkiller.ecommercebackofficeproject.customer.dto.request.CustomerRequest;
import com.spartabugkiller.ecommercebackofficeproject.customer.dto.request.CustomerResponse;
import com.spartabugkiller.ecommercebackofficeproject.customer.entity.Customer;
import com.spartabugkiller.ecommercebackofficeproject.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional
    public CustomerResponse signup(CustomerRequest request) {
        Customer customer = new Customer(request);
        return new CustomerResponse(customerRepository.save(customer));
    }

    @Transactional(readOnly = true)
    public List<CustomerResponse> getCustomers() {
        return customerRepository.findAll().stream()
                .map(CustomerResponse::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public CustomerResponse getCustomer(Long customerId) {
        Customer customer = findCustomer(customerId);
        return new CustomerResponse(customer);
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