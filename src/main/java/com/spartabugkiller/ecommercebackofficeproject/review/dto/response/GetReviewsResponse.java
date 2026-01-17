package com.spartabugkiller.ecommercebackofficeproject.review.dto.response;

import com.spartabugkiller.ecommercebackofficeproject.global.dto.PageInfo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder(access = AccessLevel.PROTECTED)
public class GetReviewsResponse {

    private final List<ReviewItems> reviews;
    private final PageInfo pageInfo;

    public static GetReviewsResponse from(List<ReviewItems> reviews, PageInfo pageInfo) {
        return GetReviewsResponse.builder()
                .reviews(reviews)
                .pageInfo(pageInfo)
                .build();
    }
}
