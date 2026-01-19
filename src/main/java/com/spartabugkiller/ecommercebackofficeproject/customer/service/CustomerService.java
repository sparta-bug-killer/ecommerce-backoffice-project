package com.spartabugkiller.ecommercebackofficeproject.customer.service;

import com.spartabugkiller.ecommercebackofficeproject.customer.dto.request.CustomerRequest;
import com.spartabugkiller.ecommercebackofficeproject.customer.dto.response.CustomerResponse;
import com.spartabugkiller.ecommercebackofficeproject.customer.dto.response.GetCustomersResponse;
import com.spartabugkiller.ecommercebackofficeproject.customer.entity.Customer;
import com.spartabugkiller.ecommercebackofficeproject.customer.entity.CustomerStatus;
import com.spartabugkiller.ecommercebackofficeproject.customer.repository.CustomerRepository;
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
                .map(obj -> new CustomerResponse((Customer) obj[0], (Long) obj[1], (Long) obj[2]))
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
        // [반영] IllegalArgumentException 제거 -> RuntimeException 사용
        Object[] result = customerRepository.findDetailWithOrderStats(id)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 고객입니다. ID: " + id));
        return new CustomerResponse((Customer) result[0], (Long) result[1], (Long) result[2]);
    }

    @Transactional
    public CustomerResponse updateCustomer(Long id, CustomerRequest request) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("수정할 고객을 찾을 수 없습니다."));

        customer.updateInfo(request.getUsername(), request.getEmail(), request.getPhone());
        return new CustomerResponse(customer, 0L, 0L);
    }

    @Transactional
    public CustomerResponse updateStatus(Long id, CustomerStatus status) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("상태를 변경할 고객을 찾을 수 없습니다."));

        customer.updateStatus(status);
        return new CustomerResponse(customer, 0L, 0L);
    }

    @Transactional
    public void deleteCustomer(Long id) {
        // [반영] 존재 여부 확인 후 삭제 (IllegalArgumentException 방지)
        if (!customerRepository.existsById(id)) {
            throw new RuntimeException("삭제할 고객이 존재하지 않습니다.");
        }
        customerRepository.deleteById(id);
    }
}