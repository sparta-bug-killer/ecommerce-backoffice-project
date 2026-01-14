package com.spartabugkiller.ecommercebackofficeproject.product.controller;

import com.spartabugkiller.ecommercebackofficeproject.product.dto.request.ProductCreateRequest;
import com.spartabugkiller.ecommercebackofficeproject.product.dto.response.ProductCreateResponse;
import com.spartabugkiller.ecommercebackofficeproject.product.service.ProductService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admins/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductCreateResponse> createProduct(
            @Valid @RequestBody ProductCreateRequest request,
            HttpSession session) {
        Long adminId = (Long) session.getAttribute("adminId");

        ProductCreateResponse response = productService.createProduct(request, adminId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
