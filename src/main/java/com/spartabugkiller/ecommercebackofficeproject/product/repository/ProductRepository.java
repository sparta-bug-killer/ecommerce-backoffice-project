package com.spartabugkiller.ecommercebackofficeproject.product.repository;

import com.spartabugkiller.ecommercebackofficeproject.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {


}
