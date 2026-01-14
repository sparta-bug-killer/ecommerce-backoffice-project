package com.spartabugkiller.ecommercebackofficeproject.product.entity;

public enum ProductType {
    ELECTRONICS("전자기기"),
    FASHION("패션/의류"),
    FOOD("식품");

    private final String description;

    ProductType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}