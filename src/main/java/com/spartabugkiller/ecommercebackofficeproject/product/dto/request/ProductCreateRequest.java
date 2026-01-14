package com.spartabugkiller.ecommercebackofficeproject.product.dto.request;

import com.spartabugkiller.ecommercebackofficeproject.product.entity.ProductStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductCreateRequest {

    @NotBlank(message = "상품명은 필수입니다.")
    private String name;

    @NotNull(message = "카테고리 ID는 필수입니다.")
    private Long categoryId;

    @NotNull(message = "가격은 필수입니다.")
    @Min(value = 0, message = "가격은 0원 이상이어야 합니다.")
    private Integer price;

    @NotNull(message = "재고는 필수입니다.")
    @Min(value = 0, message = "재고는 0개 이상이어야 합니다.")
    private Integer stock;

    @NotNull(message = "상태는 필수입니다.")
    private ProductStatus status;

    public ProductCreateRequest(String name, Long categoryId, Integer price, Integer stock, ProductStatus status) {
        this.name = name;
        this.categoryId = categoryId;
        this.price = price;
        this.stock = stock;
        this.status = status;
    }
}
