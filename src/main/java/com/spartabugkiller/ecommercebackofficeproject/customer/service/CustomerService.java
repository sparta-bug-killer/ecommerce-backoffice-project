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
        Customer customer = new Customer(request.getUsername(), request.getEmail(), request.getPhone());
        return new CustomerResponse(customerRepository.save(customer), 0L, 0L);
    }

    @Transactional(readOnly = true)
    public Page<CustomerResponse> getCustomers(String keyword, CustomerStatus status, Pageable pageable) {
        return customerRepository.findAllWithOrderStats(keyword, status, pageable)
                .map(obj -> new CustomerResponse((Customer) obj[0], (Long) obj[1], (Long) obj[2]));
    }

    @Transactional(readOnly = true)
    public CustomerResponse getCustomerDetail(Long id) {
        Object[] result = customerRepository.findDetailWithOrderStats(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 고객입니다."));
        return new CustomerResponse((Customer) result[0], (Long) result[1], (Long) result[2]);
    }

    @Transactional
    public void updateCustomer(Long id, CustomerRequest request) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("고객을 찾을 수 없습니다."));
        customer.updateInfo(request.getUsername(), request.getEmail(), request.getPhone());
    }

    @Transactional
    public void updateStatus(Long id, CustomerStatus status) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("고객을 찾을 수 없습니다."));
        customer.updateStatus(status);
    }

    @Transactional
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}