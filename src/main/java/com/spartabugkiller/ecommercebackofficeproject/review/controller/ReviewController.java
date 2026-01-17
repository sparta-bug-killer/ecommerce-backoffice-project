package com.spartabugkiller.ecommercebackofficeproject.review.controller;

import com.spartabugkiller.ecommercebackofficeproject.review.dto.response.GetReviewDetailResponse;
import com.spartabugkiller.ecommercebackofficeproject.review.dto.response.GetReviewsResponse;
import com.spartabugkiller.ecommercebackofficeproject.review.service.ReviewService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/reviews")
    public ResponseEntity<GetReviewsResponse> getReviews(
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
        return ResponseEntity.ok(response);
    }

    @GetMapping("/reviews/{reviewId}")
    public ResponseEntity<GetReviewDetailResponse> getReview(
            @PathVariable Long reviewId,
            HttpSession session
    ) {
        GetReviewDetailResponse response = reviewService.getReview(reviewId, session);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<Void> deleteReview(
            @PathVariable Long reviewId,
            HttpSession session
    ) {
        reviewService.deleteReview(reviewId, session);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
