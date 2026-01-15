package com.spartabugkiller.ecommercebackofficeproject.admin.repository;

import com.spartabugkiller.ecommercebackofficeproject.admin.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    // 이메일을 가진 Admin이 DB에 존재하는지 (회원가입 중복 체크)
    boolean existsByEmail(String email);
    // 이메일로 Admin 한 명을 찾아서 가져온다 (로그인, 상태검사, 계정조회)
    Optional<Admin> findByEmail(String email);
}
