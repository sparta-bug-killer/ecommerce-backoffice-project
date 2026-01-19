package com.spartabugkiller.ecommercebackofficeproject.customer.service;

import com.spartabugkiller.ecommercebackofficeproject.customer.dto.request.CustomerRequest;
import com.spartabugkiller.ecommercebackofficeproject.customer.dto.response.CustomerResponse;
import com.spartabugkiller.ecommercebackofficeproject.customer.dto.response.GetCustomersResponse;
import com.spartabugkiller.ecommercebackofficeproject.customer.entity.Customer;
import com.spartabugkiller.ecommercebackofficeproject.customer.entity.CustomerStatus;
import com.spartabugkiller.ecommercebackofficeproject.customer.exception.CustomerNotFoundException;
import com.spartabugkiller.ecommercebackofficeproject.customer.repository.CustomerRepository;
import com.spartabugkiller.ecommercebackofficeproject.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional(readOnly = true)
    public GetCustomersResponse getCustomers(String keyword, CustomerStatus status, int page, int size, String sortBy, String order) {
        Sort sort = order.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Object[]> result = customerRepository.findAllWithOrderStats(keyword, status, pageable);

        List<CustomerResponse> customerResponses = result.getContent().stream()
                .map(obj -> {
                    Customer customer = (Customer) obj[0];
                    Long count = (obj[1] != null) ? ((Number) obj[1]).longValue() : 0L;
                    Long sum = (obj[2] != null) ? ((Number) obj[2]).longValue() : 0L;
                    return new CustomerResponse(customer, count, sum);
                })
                .collect(Collectors.toList());

        return GetCustomersResponse.builder()
                .customers(customerResponses)
                .currentPage(result.getNumber() + 1)
                .totalElements(result.getTotalElements())
                .totalPages(result.getTotalPages())
                .size(result.getSize())
                .build();
    }

    @Transactional(readOnly = true)
    public CustomerResponse getCustomerDetail(Long id) {
        Object result = customerRepository.findDetailWithOrderStats(id)
                .orElseThrow(() -> new CustomerNotFoundException(ErrorCode.CUSTOMER_NOT_FOUND));

        Object[] row = (Object[]) result;
        Customer customer = (Customer) row[0];
        Long orderCount = (row[1] != null) ? ((Number) row[1]).longValue() : 0L;
        Long totalAmount = (row[2] != null) ? ((Number) row[2]).longValue() : 0L;

        return new CustomerResponse(customer, orderCount, totalAmount);
    }

    @Transactional
    public CustomerResponse updateCustomer(Long id, CustomerRequest request) {
        Customer customer = findById(id);
        customer.updateInfo(request.getUsername(), request.getEmail(), request.getPhone());
        customerRepository.save(customer);
        return getCustomerDetail(id);
    }

    @Transactional
    public CustomerResponse updateStatus(Long id, CustomerStatus status) {
        Customer customer = findById(id);
        customer.updateStatus(status);
        customerRepository.save(customer);
        return getCustomerDetail(id);
    }

    @Transactional
    public void deleteCustomer(Long id) {
        Customer customer = findById(id);
        customerRepository.delete(customer);
    }

    private Customer findById(Long id) {
        return customerRepository.findById(id).orElseThrow(
                () -> new CustomerNotFoundException(ErrorCode.CUSTOMER_NOT_FOUND)
        );
    }
}