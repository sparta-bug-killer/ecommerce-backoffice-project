package com.spartabugkiller.ecommercebackofficeproject.product.controller;

import com.spartabugkiller.ecommercebackofficeproject.global.common.SessionUtils;
import com.spartabugkiller.ecommercebackofficeproject.product.dto.request.ProductStatusUpdateRequest;
import com.spartabugkiller.ecommercebackofficeproject.product.dto.request.ProductUpdateRequest;
import com.spartabugkiller.ecommercebackofficeproject.product.dto.request.ProductStockUpdateRequest;
import com.spartabugkiller.ecommercebackofficeproject.product.dto.response.*;
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
import com.spartabugkiller.ecommercebackofficeproject.product.dto.request.ProductCreateRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins")
public class ProductController {

    private final ProductService productService;

    /**
     *  상품 등록
     */
    @PostMapping("/products")
    public ResponseEntity<ProductCreateResponse> createProduct(
            @Valid @RequestBody ProductCreateRequest request,
            HttpSession session) {

        // 세션 검증, adminId 추출
        Long adminId = SessionUtils.getLoginAdmin(session).getId();

        ProductCreateResponse response = productService.createProduct(request, adminId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     *  상품 상세 조회
     */
    @GetMapping("/products/{id}")
    public ResponseEntity<GetProductResponse> getProduct(
            @PathVariable Long id,
            HttpSession session) {

        // 세션 검증
        SessionUtils.getLoginAdmin(session);

        GetProductResponse response = productService.getProduct(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     *  상품 정보 수정
     */
    @PatchMapping("/products/{id}")
    public ResponseEntity<GetProductResponse> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductUpdateRequest request,
            HttpSession session) {

        // 세션 검증
        SessionUtils.getLoginAdmin(session);

        GetProductResponse response = productService.updateProduct(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     *  상품 삭제 (소프트 삭제)
     */
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable Long id,
            HttpSession session) {

        // 세션 검증
        SessionUtils.getLoginAdmin(session);

        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 상품 리스트 조회(관리자)
     */
    @GetMapping("/products")
    public ResponseEntity<Page<ProductListResponse>> getProducts(
            @RequestParam(required = false) ProductStatus status,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String keyword,
            @PageableDefault(page = 0, size = 10) Pageable pageable,
            HttpSession session
    ) {
        // 세션 검증
        SessionUtils.getLoginAdmin(session);

        return ResponseEntity.ok(
                productService.getProducts(status, categoryId, keyword, pageable)
        );
    }

    /**
     * 상품 재고 수정(관리자)
     * 추후 세션검증 예정
     */
    @PatchMapping("/products/{id}/stock")
    public ResponseEntity<ProductStockUpdateResponse> updateProductStock(
            @PathVariable("id") Long productId,
            @Valid @RequestBody ProductStockUpdateRequest request,
            HttpSession session
    ) {
        // 세션 검증
        SessionUtils.getLoginAdmin(session);

        ProductStockUpdateResponse response =
                productService.updateProductStock(productId, request);

        return ResponseEntity.ok(response);
    }

    /**
     * 상품 상태 변경(슈퍼 관리자)
     */
    @PatchMapping("/products/{id}/status")
    public ResponseEntity<ProductStatusUpdateResponse> updateProductStatus(
            @PathVariable("id") Long productId,
            @Valid @RequestBody ProductStatusUpdateRequest request,
            HttpSession session
    ) {
        // 슈퍼 관리자 세션 검증
        SessionUtils.validateSuperAdmin(session);

        ProductStatusUpdateResponse response =
                productService.updateProductStatus(productId, request);

        return ResponseEntity.ok(response);
    }
}
