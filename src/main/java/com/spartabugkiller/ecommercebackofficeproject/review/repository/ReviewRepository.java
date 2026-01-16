package com.spartabugkiller.ecommercebackofficeproject.review.repository;

import com.spartabugkiller.ecommercebackofficeproject.review.entity.Review;
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
}
