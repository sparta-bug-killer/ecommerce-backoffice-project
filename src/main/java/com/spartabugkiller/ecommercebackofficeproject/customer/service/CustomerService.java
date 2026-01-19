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

    /**
     * 고객 전체 목록 조회 (페이징 및 검색)
     * AdminService의 조회 로직 스타일 반영
     */
    @Transactional(readOnly = true)
    public GetCustomersResponse getCustomers(String keyword, CustomerStatus status, int page, int size, String sortBy, String order) {
        // 정렬 조건 생성
        Sort sort = order.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        // 통계 데이터를 포함한 복합 쿼리 실행
        Page<Object[]> result = customerRepository.findAllWithOrderStats(keyword, status, pageable);

        // 팀 표준에 맞춘 DTO 변환 (Stream 활용)
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

    /**
     * 고객 상세 정보 조회
     */
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

    /**
     * 고객 정보 수정 (이름, 이메일, 전화번호)
     * Dirty Checking 활용하여 save 호출 생략
     */
    @Transactional
    public CustomerResponse updateCustomer(Long id, CustomerRequest request) {
        Customer customer = findById(id);

        // 엔티티 내부 메서드를 통한 수정 (캡슐화)
        customer.updateInfo(request.getUsername(), request.getEmail(), request.getPhone());


        return getCustomerDetail(id);
    }

    /**
     * 고객 상태 변경
     * AdminService의 updateStatus 스타일 반영
     */
    @Transactional
    public CustomerResponse updateStatus(Long id, CustomerStatus status) {
        Customer customer = findById(id);

        customer.updateStatus(status);

        // 명시적 save 없이 트랜잭션 종료 시 자동 반영
        return getCustomerDetail(id);
    }

    /**
     * 고객 정보 삭제 (물리 삭제)
     */
    @Transactional
    public void deleteCustomer(Long id) {
        Customer customer = findById(id);
        customerRepository.delete(customer);
    }

    /**
     * 내부 사용용 ID 조회 메서드 (Private)
     */
    private Customer findById(Long id) {
        return customerRepository.findById(id).orElseThrow(
                () -> new CustomerNotFoundException(ErrorCode.CUSTOMER_NOT_FOUND)
        );
    }
}