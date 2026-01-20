package com.spartabugkiller.ecommercebackofficeproject.product.dto.response;

import com.spartabugkiller.ecommercebackofficeproject.product.entity.Product;
import com.spartabugkiller.ecommercebackofficeproject.product.entity.ProductStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ProductCreateResponse {

    private final Long id;
    private final String name;
    private final String categoryName;
    private final int price;
    private final int stock;
    private final ProductStatus status;
    private final LocalDateTime createdAt;

    public ProductCreateResponse(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.categoryName = product.getCategory().getName();
        this.price = product.getPrice();
        this.stock = product.getStock();
        this.status = product.getStatus();
        this.createdAt = product.getCreatedAt();
    }
}
