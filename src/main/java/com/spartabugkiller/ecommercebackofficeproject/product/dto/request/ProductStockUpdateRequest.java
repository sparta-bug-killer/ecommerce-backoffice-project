package com.spartabugkiller.ecommercebackofficeproject.product.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;

@Getter
public class ProductStockUpdateRequest {

    @Min(value = 0, message = "재고는 0 이상이어야 합니다.")
    @Max(value = 10000, message = "재고는 10000 이하이어야 합니다.")
    private int stock;

}
