package com.spartabugkiller.ecommercebackofficeproject.review.repository;

import com.spartabugkiller.ecommercebackofficeproject.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

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
