package com.spartabugkiller.ecommercebackofficeproject.review.repository;

import com.spartabugkiller.ecommercebackofficeproject.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 리뷰 전체 개수 + 평균 평점 (삭제 제외)
    @Query("""
        select count(r), avg(r.rating)
        from Review r
        where r.product.id = :productId
          and r.isDeleted = false
    """)
    Object countAndAvgByProductId(@Param("productId") Long productId);

    // 별점별 개수 (삭제 제외)
    @Query("""
        select r.rating, count(r)
        from Review r
        where r.product.id = :productId
          and r.isDeleted = false
        group by r.rating
    """)
    List<Object[]> countGroupByRating(@Param("productId") Long productId);

    // 최신 리뷰 3개 (customer/order 같이 가져오기) (삭제 제외)
    @Query("""
        select r
        from Review r
        join fetch r.customer c
        join fetch r.order ord
        where r.product.id = :productId
          and r.isDeleted = false
        order by r.createdAt desc
    """)
    List<Review> findLatest3ByProductId(@Param("productId") Long productId, Pageable pageable);

    @Query("""
      select r
      from Review r
      join fetch r.product
      join fetch r.customer
      where r.id = :id
          and  r.isDeleted = false
    """)
    Optional<Review> findDetailById(@Param("id") Long id);

    @Query("""
    select r
    from Review r
    join r.customer c
    join r.product p
    where r.isDeleted = false
      and (
            :keyword is null or :keyword = ''
            or lower(c.username) like lower(concat('%', :keyword, '%'))
            or lower(p.name) like lower(concat('%', :keyword, '%'))
      )
      and (:rating is null or r.rating = :rating)
""")
    Page<Review> findAllReviews(
            @Param("keyword") String keyword,
            @Param("rating") Integer rating,
            Pageable pageable
    );
}
