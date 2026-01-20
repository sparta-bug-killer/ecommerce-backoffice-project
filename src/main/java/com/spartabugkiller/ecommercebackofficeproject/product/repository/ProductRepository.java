package com.spartabugkiller.ecommercebackofficeproject.product.repository;

import com.spartabugkiller.ecommercebackofficeproject.product.entity.Product;
import com.spartabugkiller.ecommercebackofficeproject.product.entity.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // JPQL로 리스트 조회
    @Query("""
        SELECT p FROM Product p
        WHERE p.isDeleted = false
        AND (:categoryId IS NULL OR p.category.id = :categoryId)
        AND (:status IS NULL OR p.status = :status)
        AND (:keyword IS NULL OR p.name LIKE %:keyword%)
    """)
    Page<Product> findProducts(
            @Param("categoryId") Long categoryId,
            @Param("status") ProductStatus status,
            @Param("keyword") String keyword,
            Pageable pageable
    );

    // 삭제되지 않은(isDeleted = false) 상품만 ID로 조회
    Optional<Product> findByIdAndIsDeletedFalse(Long id);
}
