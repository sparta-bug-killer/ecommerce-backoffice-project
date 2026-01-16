package com.spartabugkiller.ecommercebackofficeproject.review.service;

import com.spartabugkiller.ecommercebackofficeproject.global.common.SessionUtils;
import com.spartabugkiller.ecommercebackofficeproject.global.exception.ErrorCode;
import com.spartabugkiller.ecommercebackofficeproject.review.dto.response.GetReviewDetailResponse;
import com.spartabugkiller.ecommercebackofficeproject.review.entity.Review;
import com.spartabugkiller.ecommercebackofficeproject.review.exception.ReviewNotFoundException;
import com.spartabugkiller.ecommercebackofficeproject.review.repository.ReviewRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public GetReviewDetailResponse getReview(Long reviewId, HttpSession session) {
        SessionUtils.validateAdmin(session);
        Review review = reviewRepository.findDetailById(reviewId).orElseThrow(
                () -> new ReviewNotFoundException(ErrorCode.REVIEW_NOT_FOUND)
        );
        return GetReviewDetailResponse.from(review);
    }

    private Review findById(Long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(
                () -> new ReviewNotFoundException(ErrorCode.REVIEW_NOT_FOUND)
        );
    }
}
