package com.spartabugkiller.ecommercebackofficeproject.customer.service;

import com.spartabugkiller.ecommercebackofficeproject.customer.dto.request.CustomerRequest;
import com.spartabugkiller.ecommercebackofficeproject.customer.dto.response.CustomerResponse;
import com.spartabugkiller.ecommercebackofficeproject.customer.dto.response.GetCustomersResponse;
import com.spartabugkiller.ecommercebackofficeproject.customer.entity.Customer;
import com.spartabugkiller.ecommercebackofficeproject.customer.entity.CustomerStatus;
import com.spartabugkiller.ecommercebackofficeproject.customer.exception.CustomerNotFoundException; // 커스텀 예외
import com.spartabugkiller.ecommercebackofficeproject.customer.repository.CustomerRepository;
import com.spartabugkiller.ecommercebackofficeproject.global.exception.ErrorCode; // 에러코드 사용
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
        // 팀원 스타일: 전용 메서드 호출
        Customer customer = findById(id);

        // 상세 조회의 경우 조인 데이터가 필요하므로 기존 쿼리 결과에서 예외처리만 변경
        Object[] result = customerRepository.findDetailWithOrderStats(id)
                .orElseThrow(() -> new CustomerNotFoundException(ErrorCode.CUSTOMER_NOT_FOUND));

        return new CustomerResponse((Customer) result[0], (Long) result[1], (Long) result[2]);
    }

    @Transactional
    public CustomerResponse updateCustomer(Long id, CustomerRequest request) {
        Customer customer = findById(id);
        customer.updateInfo(request.getUsername(), request.getEmail(), request.getPhone());
        return new CustomerResponse(customer, 0L, 0L);
    }

    @Transactional
    public CustomerResponse updateStatus(Long id, CustomerStatus status) {
        Customer customer = findById(id);
        customer.updateStatus(status);
        return new CustomerResponse(customer, 0L, 0L);
    }

    @Transactional
    public void deleteCustomer(Long id) {
        Customer customer = findById(id);
        customerRepository.delete(customer);
    }

    // ★ 팀원 코드(AdminService)에서 가장 중요한 부분: 공통 조회 메서드
    private Customer findById(Long id) {
        return customerRepository.findById(id).orElseThrow(
                () -> new CustomerNotFoundException(ErrorCode.CUSTOMER_NOT_FOUND)
        );
    }
}