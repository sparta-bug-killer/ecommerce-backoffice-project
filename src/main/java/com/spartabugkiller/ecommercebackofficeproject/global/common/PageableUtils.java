package com.spartabugkiller.ecommercebackofficeproject.global.common;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Set;

public class PageableUtils {

    public static Pageable makePageable(Integer page, String sortBy, String order, int pageSize, Set<String> allowed) {
        String safeSortBy = allowed.contains(sortBy) ? sortBy : "createdAt";

        Sort.Direction direction = "desc".equalsIgnoreCase(order) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, safeSortBy);
        return PageRequest.of(page, pageSize, sort);
    }
}
