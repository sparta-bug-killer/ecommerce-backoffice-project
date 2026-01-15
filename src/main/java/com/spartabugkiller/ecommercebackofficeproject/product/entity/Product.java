package com.spartabugkiller.ecommercebackofficeproject.product.entity;

import com.spartabugkiller.ecommercebackofficeproject.admin.entity.Admin;
import com.spartabugkiller.ecommercebackofficeproject.global.common.BaseEntity;
import com.spartabugkiller.ecommercebackofficeproject.global.exception.ErrorCode;
import com.spartabugkiller.ecommercebackofficeproject.product.exception.ProductInvalidCategoryException;
import com.spartabugkiller.ecommercebackofficeproject.product.exception.ProductInvalidNameException;
import com.spartabugkiller.ecommercebackofficeproject.product.exception.ProductInvalidPriceException;
import com.spartabugkiller.ecommercebackofficeproject.product.exception.ProductDiscontinuedException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "products")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int stock;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private ProductCategory category;

    @Builder
    public Product(String name, int price, int stock, ProductStatus status, Admin admin, ProductCategory category) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.status = status;
        this.admin = admin;
        this.category = category;
    }

    public void changeName(String name) {
        if (name == null || name.isBlank()) {
            throw new ProductInvalidNameException(ErrorCode.INVALID_PRODUCT_NAME);
        }
        this.name = name;
    }

    public void changePrice(int price) {
        if (price < 0) {
            throw new ProductInvalidPriceException(ErrorCode.INVALID_PRODUCT_PRICE);
        }
        this.price = price;
    }

    public void changeCategory(ProductCategory category) {
        if (category == null) {
            throw new ProductInvalidCategoryException(ErrorCode.INVALID_PRODUCT_CATEGORY);
        }
        this.category = category;
    }

    // 재고 변경
    public void updateStock(int stock) {

        // 상태가 단종일때는 재고 변경 X
        if(this.status == ProductStatus.DISCONTINUED) {
            throw new ProductDiscontinuedException(ErrorCode.PRODUCT_DISCONTINUED);
        }

        this.stock = stock;

        // 재고 기준으로 상태 변경
        if(stock <= 0) {
            this.status = ProductStatus.OUT_OF_STOCK;
        } else {
            this.status = ProductStatus.ON_SALE;
        }
    }
}
