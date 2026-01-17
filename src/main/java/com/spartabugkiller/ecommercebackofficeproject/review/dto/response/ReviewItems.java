package com.spartabugkiller.ecommercebackofficeproject.review.dto.response;

import com.spartabugkiller.ecommercebackofficeproject.review.entity.Review;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder(access = AccessLevel.PROTECTED)
public class ReviewItems {

    private final Long id;
    private final String orderNum;
    private final String customerName;
    private final String productName;
    private final int rating;
    private final String description;
    private final LocalDateTime createdAt;

    public static ReviewItems from(Review review) {
        return ReviewItems.builder()
                .id(review.getId())
                .orderNum(review.getOrder().getOrderNum())
                .customerName(review.getCustomer().getUsername())
                .productName(review.getProduct().getName())
                .rating(review.getRating())
                .description(review.getDescription())
                .createdAt(review.getCreatedAt())
                .build();
    }
}
