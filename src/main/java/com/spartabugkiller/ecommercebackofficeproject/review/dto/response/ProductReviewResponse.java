package com.spartabugkiller.ecommercebackofficeproject.review.dto.response;

import com.spartabugkiller.ecommercebackofficeproject.review.entity.Review;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Builder(access = AccessLevel.PROTECTED)
public class ProductReviewResponse {

    private Long productId;
    private ReviewSummary summary;
    private List<LatestReview> lastReviews;

    public static ProductReviewResponse of(
            Long productId,
            ReviewSummary summary,
            List<LatestReview> lastReviews
    ) {
        return ProductReviewResponse.builder()
                .productId(productId)
                .summary(summary)
                .lastReviews(lastReviews)
                .build();
    }

    @Getter
    @Builder(access = AccessLevel.PROTECTED)
    public static class ReviewSummary {

        private double avgRating;
        private long totalCount;
        private Map<Integer, Long> ratingCounts;

        public static ReviewSummary of(
                double avgRating,
                long totalCount,
                Map<Integer, Long> ratingCounts
        ) {
            return ReviewSummary.builder()
                    .avgRating(avgRating)
                    .totalCount(totalCount)
                    .ratingCounts(ratingCounts)
                    .build();
        }
    }

    @Getter
    @Builder(access = AccessLevel.PROTECTED)
    public static class LatestReview {

        private Long id;                    // 고유 식별자
        private String orderNum;            // 주문번호
        private String username;            // 고객명
        private int rating;                 // 평점
        private String description;         // 리뷰 내용
        private LocalDateTime createdAt;    // 작성일

        public static LatestReview from(Review review) {
            return LatestReview.builder()
                    .id(review.getId())
                    .orderNum(review.getOrder().getOrderNum())
                    .username(review.getCustomer().getUsername())
                    .rating(review.getRating())
                    .description(review.getDescription())
                    .createdAt(review.getCreatedAt())
                    .build();
        }
    }
}
