package com.spartabugkiller.ecommercebackofficeproject.customer.service;

import com.spartabugkiller.ecommercebackofficeproject.customer.dto.request.CustomerRequest;
import com.spartabugkiller.ecommercebackofficeproject.customer.dto.request.CustomerResponse;
import com.spartabugkiller.ecommercebackofficeproject.customer.entity.Customer;
import com.spartabugkiller.ecommercebackofficeproject.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    /**
     * 회원가입 로직
     */
    @Transactional
    public CustomerResponse signUp(CustomerRequest request) {
        // 1. 이메일 중복 확인 (이미 가입된 이메일인지 체크)
        if (customerRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        // 2. DTO 정보를 바탕으로 Customer 엔티티 생성
        Customer customer = new Customer(
                request.getName(),
                request.getEmail(),
                request.getPassword(),
                request.getPhoneNumber()
        );

        // 3. DB에 저장
        Customer savedCustomer = customerRepository.save(customer);

        // 4. 저장된 결과를 다시 응답 가방(Response)에 담아서 리턴
        return new CustomerResponse(savedCustomer);
    }
}