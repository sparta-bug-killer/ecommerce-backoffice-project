package com.spartabugkiller.ecommercebackofficeproject.customer.service;

import com.spartabugkiller.ecommercebackofficeproject.customer.dto.request.CustomerRequest;
import com.spartabugkiller.ecommercebackofficeproject.customer.dto.response.CustomerResponse;
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
    // 나중에 팀원이 OrderRepository를 만들면 여기에 추가하세요!
    // private final OrderRepository orderRepository;

    @Transactional
    public CustomerResponse signup(CustomerRequest request) {
        Customer customer = new Customer(request);
        // 생성자가 바뀌었으므로 0L, 0L을 추가합니다.
        return new CustomerResponse(customerRepository.save(customer), 0L, 0L);
    }

    // 페이징 적용된 목록 조회 (도전 기능 반영)
    @Transactional(readOnly = true)
    public Page<CustomerResponse> getCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable)
                .map(customer -> new CustomerResponse(customer, 0L, 0L));
    }

    // 상세 조회 (도전 기능 반영)
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
    public CustomerResponse updateStatus(Long customerId, String status) {
        Customer customer = findCustomer(customerId);
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