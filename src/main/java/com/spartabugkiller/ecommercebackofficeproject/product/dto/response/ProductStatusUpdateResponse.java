package com.spartabugkiller.ecommercebackofficeproject.product.dto.response;

import com.spartabugkiller.ecommercebackofficeproject.product.entity.Product;
import com.spartabugkiller.ecommercebackofficeproject.product.entity.ProductStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductStatusUpdateResponse {

    private final Long productId;
    private final ProductStatus status;

    public static ProductStatusUpdateResponse from(Product product) {
        return ProductStatusUpdateResponse.builder()
                .productId(product.getId())
                .status(product.getStatus())
                .build();
    }
}
