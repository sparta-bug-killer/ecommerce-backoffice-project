package com.spartabugkiller.ecommercebackofficeproject.product.service;

import com.spartabugkiller.ecommercebackofficeproject.product.dto.response.ProductListResponse;
import com.spartabugkiller.ecommercebackofficeproject.product.entity.ProductStatus;
import com.spartabugkiller.ecommercebackofficeproject.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    /**
     *  상품 리스트 조회
     */
    @Transactional(readOnly = true)
    public Page<ProductListResponse> getProducts (ProductStatus status, Long categoryId, String keyword, Pageable pageable) {

        return productRepository
                .findProducts(categoryId, status, keyword, pageable)
                .map(ProductListResponse::new);
    }
}
