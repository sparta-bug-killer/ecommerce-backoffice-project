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
        // 수정된 생성자 사용
        Customer customer = new Customer(request);
        Customer savedCustomer = customerRepository.save(customer);
        return new CustomerResponse(savedCustomer);
    }

    @Transactional(readOnly = true)
    public List<CustomerResponse> getCustomers() {
        return customerRepository.findAll().stream()
                .map(CustomerResponse::new)
                .toList();
    }
}