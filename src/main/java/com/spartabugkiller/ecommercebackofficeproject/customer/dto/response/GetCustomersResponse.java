package com.spartabugkiller.ecommercebackofficeproject.customer.dto.response;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
public class GetCustomersResponse {
    private List<CustomerResponse> customers; // 고객 정보 리스트
    private int currentPage;                  // 현재 페이지 번호
    private long totalElements;               // 전체 고객 수
    private int totalPages;                   // 전체 페이지 수
    private int size;                         // 한 페이지당 노출 개수
}