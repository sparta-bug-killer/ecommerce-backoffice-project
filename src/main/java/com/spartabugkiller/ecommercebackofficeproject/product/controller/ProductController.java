package com.spartabugkiller.ecommercebackofficeproject.product.controller;

import com.spartabugkiller.ecommercebackofficeproject.product.dto.response.ProductListResponse;
import com.spartabugkiller.ecommercebackofficeproject.product.entity.ProductStatus;
import com.spartabugkiller.ecommercebackofficeproject.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.data.domain.Pageable;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins")
public class ProductController {

    private final ProductService productService;

    /**
     * 상품 리스트 조회(관리자)
     */
    @GetMapping("/products")
    public ResponseEntity<Page<ProductListResponse>> getProducts(
            @RequestParam(required = false) ProductStatus status,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String keyword,
            @PageableDefault(page = 0, size = 10) Pageable pageable
    ) {
        return ResponseEntity.ok(
                productService.getProducts(status, categoryId, keyword, pageable)
        );
    }
}
