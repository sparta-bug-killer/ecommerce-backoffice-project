package com.spartabugkiller.ecommercebackofficeproject.review.controller;

import com.spartabugkiller.ecommercebackofficeproject.global.dto.ApiResponse;
import com.spartabugkiller.ecommercebackofficeproject.review.dto.response.GetReviewDetailResponse;
import com.spartabugkiller.ecommercebackofficeproject.review.dto.response.ProductReviewResponse;
import com.spartabugkiller.ecommercebackofficeproject.review.dto.response.GetReviewsResponse;
import com.spartabugkiller.ecommercebackofficeproject.review.service.ReviewService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins")
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * 상품별 리뷰 조회 API
     */
    @GetMapping("/products/{productId}/reviews")
    public ResponseEntity<ProductReviewResponse> getProductDetailWithReviews(
            @PathVariable Long productId,
            HttpSession session
    ) {
        ProductReviewResponse response = reviewService.getProductReviews(productId, session);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/reviews")
    public ResponseEntity<ApiResponse<GetReviewsResponse>> getReviews(
            HttpSession session,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(required = false, defaultValue = "desc") String order,
            @RequestParam(required = false) Integer rating
    ) {
        int pageIndex = Math.max(page - 1, 0);
        GetReviewsResponse response = reviewService.getReviews(session, keyword, pageIndex, size, sortBy, order, rating);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @GetMapping("/reviews/{reviewId}")
    public ResponseEntity<ApiResponse<GetReviewDetailResponse>> getReview(
            @PathVariable Long reviewId,
            HttpSession session
    ) {
        GetReviewDetailResponse response = reviewService.getReview(reviewId, session);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<ApiResponse<Void>> deleteReview(
            @PathVariable Long reviewId,
            HttpSession session
    ) {
        reviewService.deleteReview(reviewId, session);
        return ResponseEntity.ok(ApiResponse.noContent());
    }
}
