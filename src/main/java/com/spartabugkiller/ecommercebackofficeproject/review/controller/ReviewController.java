package com.spartabugkiller.ecommercebackofficeproject.review.controller;

import com.spartabugkiller.ecommercebackofficeproject.review.dto.response.GetReviewDetailResponse;
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
