package com.spartabugkiller.ecommercebackofficeproject.product.service;

import com.spartabugkiller.ecommercebackofficeproject.admin.entity.Admin;
import com.spartabugkiller.ecommercebackofficeproject.admin.exception.AdminNotFoundException;
import com.spartabugkiller.ecommercebackofficeproject.admin.repository.AdminRepository;
import com.spartabugkiller.ecommercebackofficeproject.global.exception.ErrorCode;
import com.spartabugkiller.ecommercebackofficeproject.product.dto.request.ProductCreateRequest;
import com.spartabugkiller.ecommercebackofficeproject.product.dto.request.ProductStatusUpdateRequest;
import com.spartabugkiller.ecommercebackofficeproject.product.dto.request.ProductStockUpdateRequest;
import com.spartabugkiller.ecommercebackofficeproject.product.dto.request.ProductUpdateRequest;
import com.spartabugkiller.ecommercebackofficeproject.product.dto.response.*;
import com.spartabugkiller.ecommercebackofficeproject.product.entity.Product;
import com.spartabugkiller.ecommercebackofficeproject.product.entity.ProductCategory;
import com.spartabugkiller.ecommercebackofficeproject.product.exception.CategoryNotFoundException;
import com.spartabugkiller.ecommercebackofficeproject.product.exception.ProductNotFoundException;
import com.spartabugkiller.ecommercebackofficeproject.product.repository.ProductCategoryRepository;
import com.spartabugkiller.ecommercebackofficeproject.product.repository.ProductRepository;
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

    /**
     *  상품 등록
     */
    @Transactional
    public ProductCreateResponse createProduct(ProductCreateRequest request, Long adminId) {
        // 관리자 확인
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new AdminNotFoundException(ErrorCode.ADMIN_NOT_FOUND));

        // 카테고리 조회
        ProductCategory category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(ErrorCode.CATEGORY_NOT_FOUND));

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
     *  상품 상세 조회
     */
    @Transactional(readOnly = true)
    public GetProductResponse getProduct(Long productId) {
        // 상품 조회
        Product product = productRepository.findByIdAndIsDeletedFalse(productId)
                .orElseThrow(() -> new ProductNotFoundException(ErrorCode.PRODUCT_NOT_FOUND));

        return new GetProductResponse(product);
    }

    /**
     *  상품 정보 수정
     */
    @Transactional
    public GetProductResponse updateProduct(Long productId, ProductUpdateRequest request) {
        // 상품 조회
        Product product = productRepository.findByIdAndIsDeletedFalse(productId)
                .orElseThrow(() -> new ProductNotFoundException(ErrorCode.PRODUCT_NOT_FOUND));

        // 입력된 필드만 선택적으로 수정
        if (request.getName() != null) {
            product.changeName(request.getName());
        }

        if (request.getPrice() != null) {
            product.changePrice(request.getPrice());
        }

        if (request.getCategoryId() != null) {
            ProductCategory category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new CategoryNotFoundException(ErrorCode.CATEGORY_NOT_FOUND));
            product.changeCategory(category);
        }

        return new GetProductResponse(product);
    }

    /**
     *  상품 삭제
     */
    @Transactional
    public void deleteProduct(Long productId) {
        // 상품 조회
        Product product = productRepository.findByIdAndIsDeletedFalse(productId)
                .orElseThrow(() -> new ProductNotFoundException(ErrorCode.PRODUCT_NOT_FOUND));

        product.delete();
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

    /**
     *  상품 재고 변경
     */
    @Transactional
    public ProductStockUpdateResponse updateProductStock(Long productId, ProductStockUpdateRequest request) {

        // 예외처리
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(ErrorCode.PRODUCT_NOT_FOUND));

        // 재고 변경 로직
        product.updateStock(request.getStock());

        // 응답 DTO 변환
        return ProductStockUpdateResponse.from(product);
    }

    /**
     * 상품 상태 변경
     */
    @Transactional
    public ProductStatusUpdateResponse updateProductStatus(Long productId, ProductStatusUpdateRequest request) {

        // 예외 처리
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(ErrorCode.PRODUCT_NOT_FOUND)
                );

        // 상품 상태 변경
        product.updateStatus(request.getStatus());

        // 응답 DTO 변환
        return ProductStatusUpdateResponse.from(product);
    }
}
