package com.spartabugkiller.ecommercebackofficeproject.product.service;

import com.spartabugkiller.ecommercebackofficeproject.admin.entity.Admin;
import com.spartabugkiller.ecommercebackofficeproject.admin.exception.AdminNotFoundException;
import com.spartabugkiller.ecommercebackofficeproject.admin.repository.AdminRepository;
import com.spartabugkiller.ecommercebackofficeproject.global.exception.ErrorCode;
import com.spartabugkiller.ecommercebackofficeproject.product.dto.request.ProductCreateRequest;
import com.spartabugkiller.ecommercebackofficeproject.product.dto.response.ProductCreateResponse;
import com.spartabugkiller.ecommercebackofficeproject.product.entity.Product;
import com.spartabugkiller.ecommercebackofficeproject.product.entity.ProductCategory;
import com.spartabugkiller.ecommercebackofficeproject.product.exception.CategoryNotFoundException;
import com.spartabugkiller.ecommercebackofficeproject.product.repository.ProductCategoryRepository;
import com.spartabugkiller.ecommercebackofficeproject.product.repository.ProductRepository;
import com.spartabugkiller.ecommercebackofficeproject.product.dto.response.ProductListResponse;
import com.spartabugkiller.ecommercebackofficeproject.product.entity.ProductStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository categoryRepository;
    private final AdminRepository adminRepository;

    /** 상품 등록 */
    @Transactional
    public ProductCreateResponse createProduct(ProductCreateRequest request, Long adminId) {
        ProductCategory category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(ErrorCode.CATEGORY_NOT_FOUND));

        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new AdminNotFoundException(ErrorCode.ADMIN_NOT_FOUND));

        Product product = Product.builder()
                .name(request.getName())
                .category(category)
                .price(request.getPrice())
                .stock(request.getStock())
                .status(request.getStatus())
                .admin(admin)
                .build();

        Product savedProduct = productRepository.save(product);
        return new ProductCreateResponse(savedProduct);
    }

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
