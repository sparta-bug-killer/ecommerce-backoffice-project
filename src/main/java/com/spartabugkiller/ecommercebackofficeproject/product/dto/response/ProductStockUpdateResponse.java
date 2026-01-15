package com.spartabugkiller.ecommercebackofficeproject.product.dto.response;

import com.spartabugkiller.ecommercebackofficeproject.product.entity.Product;
import com.spartabugkiller.ecommercebackofficeproject.product.entity.ProductStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductStockUpdateResponse {

    private final Long productId;
    private final int stock;
    private final ProductStatus status;

    public static ProductStockUpdateResponse from(Product product) {
        return ProductStockUpdateResponse.builder()
                .productId(product.getId())
                .stock(product.getStock())
                .status(product.getStatus())
                .build();
    }
}

