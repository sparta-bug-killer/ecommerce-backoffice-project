package com.spartabugkiller.ecommercebackofficeproject.review.dto.response;

import com.spartabugkiller.ecommercebackofficeproject.review.entity.Review;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder(access = AccessLevel.PROTECTED)
public class GetReviewDetailResponse {

    private final Long id;
    private final String productName;
    private final String customerName;
    private final String customerEmail;
    private final LocalDateTime createdAt;
    private final int rating;
    private final String description;

    public static GetReviewDetailResponse from(Review review) {
        return GetReviewDetailResponse.builder()
                .id(review.getId())
                .productName(review.getProduct().getName())
                .customerName(review.getCustomer().getUsername())
                .customerEmail(review.getCustomer().getEmail())
                .createdAt(review.getCreatedAt())
                .rating(review.getRating())
                .description(review.getDescription())
                .build();
    }
}
