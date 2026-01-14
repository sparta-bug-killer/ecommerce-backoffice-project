package com.spartabugkiller.ecommercebackofficeproject.product.dto.response;

import com.spartabugkiller.ecommercebackofficeproject.product.entity.Product;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ProductListResponse {

    private final Long productId;
    private final String name;
    private final int price;
    private final int stock;
    private final String status;
    private final String categoryName;
    private final LocalDateTime createdAt;
    private final String adminName;

    public ProductListResponse(Product product) {
        this.productId = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.stock = product.getStock();
        this.status = product.getStatus().name();
        this.categoryName = product.getCategory().getName();
        this.createdAt = product.getCreatedAt();
        this.adminName = product.getAdmin().getName();
    }
}
