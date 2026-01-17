package com.spartabugkiller.ecommercebackofficeproject.review.service;

import com.spartabugkiller.ecommercebackofficeproject.global.common.SessionUtils;
import com.spartabugkiller.ecommercebackofficeproject.global.exception.ErrorCode;
import com.spartabugkiller.ecommercebackofficeproject.product.exception.ProductNotFoundException;
import com.spartabugkiller.ecommercebackofficeproject.product.repository.ProductRepository;
import com.spartabugkiller.ecommercebackofficeproject.review.dto.response.GetReviewDetailResponse;
import com.spartabugkiller.ecommercebackofficeproject.review.dto.response.ProductReviewResponse;
import com.spartabugkiller.ecommercebackofficeproject.review.entity.Review;
import com.spartabugkiller.ecommercebackofficeproject.review.exception.ReviewNotFoundException;
import com.spartabugkiller.ecommercebackofficeproject.review.repository.ReviewRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;

    /**
     * 상품별 리뷰 조회 API
     */
    @Transactional(readOnly = true)
    public ProductReviewResponse getProductReviews(Long productId, HttpSession session) {
        SessionUtils.validateAdmin(session);

        // 상품 존재 여부 확인
        productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(ErrorCode.PRODUCT_NOT_FOUND));

        // 리뷰 통계 조회 (전체 리뷰 개수, 평균 평점)
        Object raw = reviewRepository.countAndAvgByProductId(productId);
        Object[] countAndAvg = (raw == null)
                ? new Object[]{0L, null}
                : (Object[]) raw;

        long totalCount = ((Number) countAndAvg[0]).longValue(); // 전체 리뷰 개수
        Double avgRaw = (Double) countAndAvg[1]; // 평균 평점

        double avgRating = (avgRaw == null)
                ? 0.0
                : BigDecimal.valueOf(avgRaw).setScale(1, RoundingMode.HALF_UP).doubleValue();

        // 별점별 리뷰 개수 (1~5점)
        Map<Integer, Long> ratingCounts = new LinkedHashMap<>();
        for (int i = 1; i <= 5; i++) {
            ratingCounts.put(i, 0L);
        }

        List<Object[]> grouped = reviewRepository.countGroupByRating(productId);
        for (Object[] row : grouped) {
            Integer rating = (Integer) row[0];
            Long cnt = ((Number) row[1]).longValue();
            ratingCounts.put(rating, cnt);
        }

        ProductReviewResponse.ReviewSummary summary =
                ProductReviewResponse.ReviewSummary.of(avgRating, totalCount, ratingCounts);

        // 최신 리뷰 3개 조회 (작성일 기준 내림차순)
        List<Review> latest3 =
                reviewRepository.findLatest3ByProductId(
                        productId,
                        PageRequest.of(0, 3)
                );

        List<ProductReviewResponse.LatestReview> latestReviews =
                latest3.stream()
                        .map(ProductReviewResponse.LatestReview::from)
                        .toList();

        // 응답 반환
        return ProductReviewResponse.of(productId, summary, latestReviews);
    }

    @Transactional(readOnly = true)
    public GetReviewDetailResponse getReview(Long reviewId, HttpSession session) {
        SessionUtils.validateAdmin(session);
        Review review = reviewRepository.findDetailById(reviewId).orElseThrow(
                () -> new ReviewNotFoundException(ErrorCode.REVIEW_NOT_FOUND)
        );
        return GetReviewDetailResponse.from(review);
    }

    @Transactional
    public void deleteReview(Long reviewId, HttpSession session) {
        SessionUtils.validateAdmin(session);
        Review review = findById(reviewId);
        review.delete();
    }

    private Review findById(Long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(
                () -> new ReviewNotFoundException(ErrorCode.REVIEW_NOT_FOUND)
        );
    }
}
