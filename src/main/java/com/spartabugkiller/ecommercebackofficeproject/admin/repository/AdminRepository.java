package com.spartabugkiller.ecommercebackofficeproject.admin.repository;

import com.spartabugkiller.ecommercebackofficeproject.admin.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    boolean existsByEmail(String email);
    Optional<Admin> findByEmail(String email);
}
