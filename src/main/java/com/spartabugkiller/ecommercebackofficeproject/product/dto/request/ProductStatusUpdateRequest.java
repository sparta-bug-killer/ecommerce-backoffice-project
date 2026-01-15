package com.spartabugkiller.ecommercebackofficeproject.product.dto.request;

import com.spartabugkiller.ecommercebackofficeproject.product.entity.ProductStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ProductStatusUpdateRequest {

    @NotNull(message = "상품 상태는 필수 입력입니다.")
    private ProductStatus status;
}
