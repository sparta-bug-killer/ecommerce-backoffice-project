package com.spartabugkiller.ecommercebackofficeproject.review.service;

import com.spartabugkiller.ecommercebackofficeproject.global.common.SessionUtils;
import com.spartabugkiller.ecommercebackofficeproject.global.dto.PageInfo;
import com.spartabugkiller.ecommercebackofficeproject.global.exception.ErrorCode;
import com.spartabugkiller.ecommercebackofficeproject.review.dto.response.GetReviewDetailResponse;
import com.spartabugkiller.ecommercebackofficeproject.review.dto.response.GetReviewsResponse;
import com.spartabugkiller.ecommercebackofficeproject.review.dto.response.ReviewItems;
import com.spartabugkiller.ecommercebackofficeproject.review.entity.Review;
import com.spartabugkiller.ecommercebackofficeproject.review.exception.ReviewNotFoundException;
import com.spartabugkiller.ecommercebackofficeproject.review.repository.ReviewRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static com.spartabugkiller.ecommercebackofficeproject.global.common.PageableUtils.makePageable;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Transactional(readOnly = true)
    public GetReviewsResponse getReviews(HttpSession session, String keyword, Integer page, Integer size, String sortBy, String order, Integer rating) {
        SessionUtils.validateAdmin(session);

        int pageSize = (size == null || size < 1) ? 10 : size;

        Set<String> allowed = Set.of("rating","createdAt");
        Pageable pageable = makePageable(page, sortBy, order, pageSize, allowed);

        Page<Review> pageResult =  reviewRepository.findAllReviews(keyword, rating, pageable);

        List<ReviewItems> items = pageResult.getContent().stream()
                .map(ReviewItems::from)
                .toList();

        PageInfo pageInfo = PageInfo.from(pageResult);

        return GetReviewsResponse.from(items, pageInfo);
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
