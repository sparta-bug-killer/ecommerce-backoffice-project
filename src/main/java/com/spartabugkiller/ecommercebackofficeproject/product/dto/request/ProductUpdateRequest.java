package com.spartabugkiller.ecommercebackofficeproject.product.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductUpdateRequest {

    private String name;

    private Long categoryId;

    @Min(value = 0, message = "가격은 0원 이상이어야 합니다.")
    private Integer price;

    public ProductUpdateRequest(String name, Long categoryId, Integer price) {
        this.name = name;
        this.categoryId = categoryId;
        this.price = price;
    }
}
