package com.spartabugkiller.ecommercebackofficeproject.product.controller;

import com.spartabugkiller.ecommercebackofficeproject.product.dto.request.ProductUpdateRequest;
import com.spartabugkiller.ecommercebackofficeproject.product.dto.request.ProductStockUpdateRequest;
import com.spartabugkiller.ecommercebackofficeproject.product.dto.response.GetProductResponse;
import com.spartabugkiller.ecommercebackofficeproject.product.dto.response.ProductListResponse;
import com.spartabugkiller.ecommercebackofficeproject.product.dto.response.ProductStockUpdateResponse;
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
import com.spartabugkiller.ecommercebackofficeproject.product.dto.response.ProductCreateResponse;
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
        Long adminId = (Long) session.getAttribute("adminId");

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
        Long adminId = (Long) session.getAttribute("adminId");

        GetProductResponse response = productService.getProduct(id, adminId);
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
        Long adminId = (Long) session.getAttribute("adminId");

        GetProductResponse response = productService.updateProduct(id, request, adminId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     *  상품 삭제
     */
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable Long id,
            HttpSession session) {
        Long adminId = 1L;
//                (Long) session.getAttribute("adminId");

        productService.deleteProduct(id, adminId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 상품 리스트 조회(관리자)
     * 추후 세션검증 예정
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

    /**
     * 상품 재고 수정(관리자)
     * 추후 세션검증 예정
     */
    @PatchMapping("/products/{id}/stock")
    public ResponseEntity<ProductStockUpdateResponse> updateProductStock(
            @PathVariable("id") Long productId,
            @Valid @RequestBody ProductStockUpdateRequest request
    ) {
        ProductStockUpdateResponse response =
                productService.updateProductStock(productId, request);

        return ResponseEntity.ok(response);
    }
}
