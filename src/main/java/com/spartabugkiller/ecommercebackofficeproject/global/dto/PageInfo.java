package com.spartabugkiller.ecommercebackofficeproject.global.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
@Builder(access = AccessLevel.PROTECTED)
public class PageInfo {

    private int page;
    private int size;
    private long totalCount;
    private int totalPages;

    public static PageInfo from(Page<?> page) {
        return PageInfo.builder()
                .page(page.getNumber() + 1)
                .size(page.getSize())
                .totalCount(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }
}
