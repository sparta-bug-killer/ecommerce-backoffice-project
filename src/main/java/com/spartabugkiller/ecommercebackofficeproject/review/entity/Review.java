package com.spartabugkiller.ecommercebackofficeproject.review.entity;

import com.spartabugkiller.ecommercebackofficeproject.customer.entity.Customer;
import com.spartabugkiller.ecommercebackofficeproject.global.common.BaseEntity;
import com.spartabugkiller.ecommercebackofficeproject.order.entity.Order;
import com.spartabugkiller.ecommercebackofficeproject.product.entity.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "reviews")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer rating;

    @Column(nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    public Review(Customer customer, Product product, Order order, Integer rating, String description) {
        this.customer = customer;
        this.product = product;
        this.order = order;
        this.rating = rating;
        this.description = description;
    }

    // 리뷰 수정
    public void update(Integer rating, String description) {
        this.rating = rating;
        this.description = description;
    }

    // 리뷰 삭제
    public void deleteReview() {
        super.delete();
    }
}
