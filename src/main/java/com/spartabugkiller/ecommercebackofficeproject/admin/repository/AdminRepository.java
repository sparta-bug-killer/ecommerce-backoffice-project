package com.spartabugkiller.ecommercebackofficeproject.admin.repository;

import com.spartabugkiller.ecommercebackofficeproject.admin.entity.Admin;
import com.spartabugkiller.ecommercebackofficeproject.admin.entity.AdminRole;
import com.spartabugkiller.ecommercebackofficeproject.admin.entity.AdminStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    // 이메일을 가진 Admin이 DB에 존재하는지 (회원가입 중복 체크)
    boolean existsByEmail(String email);
    // 이메일로 Admin 한 명을 찾아서 가져온다 (로그인, 상태검사, 계정조회)
    Optional<Admin> findByEmail(String email);

    @Query("""
        SELECT p FROM Admin p
        WHERE p.isDeleted = false
        AND (:status IS NULL OR p.status = :status)
        AND (:role IS NULL OR p.role = :role)
        AND (:keyword IS NULL OR :keyword = ''
               OR lower(p.name)  like lower(concat('%', :keyword, '%'))
               OR lower(p.email) like lower(concat('%', :keyword, '%')))
    """)
    Page<Admin> findAllAdmins(
            @Param("keyword") String keyword,
            @Param("role") AdminRole role,
            @Param("status") AdminStatus status,
            Pageable pageable);
}
