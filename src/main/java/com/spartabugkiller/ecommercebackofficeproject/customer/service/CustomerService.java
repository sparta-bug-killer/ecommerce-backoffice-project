package com.spartabugkiller.ecommercebackofficeproject.customer.service;

import com.spartabugkiller.ecommercebackofficeproject.customer.dto.request.CustomerRequest;
import com.spartabugkiller.ecommercebackofficeproject.customer.dto.response.CustomerResponse;
import com.spartabugkiller.ecommercebackofficeproject.customer.dto.response.GetCustomersResponse;
import com.spartabugkiller.ecommercebackofficeproject.customer.entity.Customer;
import com.spartabugkiller.ecommercebackofficeproject.customer.entity.CustomerStatus;
import com.spartabugkiller.ecommercebackofficeproject.customer.repository.CustomerRepository;
import jakarta.servlet.http.HttpSession;
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
    public GetCustomersResponse getCustomers(HttpSession session, String keyword, CustomerStatus status, int page, int size, String sortBy, String order) {
        // 1. 팀원들 방식대로 정렬 설정
        Sort sort = order.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        // 2. 데이터 조회
        Page<Object[]> result = customerRepository.findAllWithOrderStats(keyword, status, pageable);

        // 3. DTO 리스트로 변환
        List<CustomerResponse> customerResponses = result.getContent().stream()
                .map(obj -> new CustomerResponse((Customer) obj[0], (Long) obj[1], (Long) obj[2]))
                .collect(Collectors.toList());

        // 4. 팀 규격 DTO(GetCustomersResponse)로 감싸서 반환
        return GetCustomersResponse.builder()
                .customers(customerResponses)
                .currentPage(result.getNumber() + 1)
                .totalElements(result.getTotalElements())
                .totalPages(result.getTotalPages())
                .size(result.getSize())
                .build();
    }

    @Transactional(readOnly = true)
    public CustomerResponse getCustomerDetail(Long id, HttpSession session) {
        Object[] result = customerRepository.findDetailWithOrderStats(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 고객입니다."));
        return new CustomerResponse((Customer) result[0], (Long) result[1], (Long) result[2]);
    }

    @Transactional
    public CustomerResponse updateCustomer(Long id, CustomerRequest request, HttpSession session) {
        Customer customer = customerRepository.findById(id).orElseThrow();
        customer.updateInfo(request.getUsername(), request.getEmail(), request.getPhone());
        return new CustomerResponse(customer, 0L, 0L); // 수정 후 응답 객체 반환
    }

    @Transactional
    public CustomerResponse updateStatus(Long id, CustomerStatus status, HttpSession session) {
        Customer customer = customerRepository.findById(id).orElseThrow();
        customer.updateStatus(status);
        return new CustomerResponse(customer, 0L, 0L);
    }

    @Transactional
    public void deleteCustomer(Long id, HttpSession session) {
        customerRepository.deleteById(id);
    }
}