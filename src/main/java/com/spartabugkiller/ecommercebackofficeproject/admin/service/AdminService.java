package com.spartabugkiller.ecommercebackofficeproject.admin.service;

import com.spartabugkiller.ecommercebackofficeproject.admin.dto.SessionAdmin;
import com.spartabugkiller.ecommercebackofficeproject.admin.dto.request.SigninAdminRequest;
import com.spartabugkiller.ecommercebackofficeproject.admin.dto.request.SignupAdminRequest;
import com.spartabugkiller.ecommercebackofficeproject.admin.dto.response.SigninAdminResponse;
import com.spartabugkiller.ecommercebackofficeproject.admin.dto.response.SignupAdminResponse;
import com.spartabugkiller.ecommercebackofficeproject.admin.entity.Admin;
import com.spartabugkiller.ecommercebackofficeproject.admin.entity.AdminStatus;
import com.spartabugkiller.ecommercebackofficeproject.admin.repository.AdminRepository;
import com.spartabugkiller.ecommercebackofficeproject.global.config.PasswordEncoder;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SignupAdminResponse signup(SignupAdminRequest request) {

        if (adminRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        String encodedPw = passwordEncoder.encode(request.getPassword());

        Admin admin = new Admin(
                request.getName(),
                request.getEmail(),
                encodedPw,
                request.getPhoneNumber(),
                request.getRole()
        );

        Admin saved = adminRepository.save(admin);
        return SignupAdminResponse.from(saved);
    }

    @Transactional(readOnly = true)
    public SigninAdminResponse signin(SigninAdminRequest request, HttpSession session) {

        Admin admin = adminRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalStateException("유효하지 않은 이메일입니다."));

        // 상태 체크
        if (admin.getStatus() == AdminStatus.PENDING) {
            throw new IllegalStateException("승인 대기 중인 계정입니다.");
        }
        if (admin.getStatus() == AdminStatus.REJECTED) {
            throw new IllegalStateException("승인 거절된 계정입니다.");
        }
        if (admin.getStatus() == AdminStatus.BLOCKED) {
            throw new IllegalStateException("정지된 계정입니다.");
        }

        // 비밀번호가 같지 않으면 (✅ bcrypt 비교)
        if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }

        // 비밀번호가 같으면 → 응답 DTO
        return new SigninAdminResponse(
                admin.getId(),
                admin.getName(),
                admin.getEmail(),
                admin.getRole(),
                admin.getStatus()
        );
    }

    @Transactional
    public void logout(HttpSession session) {
        session.invalidate();   // 세션 통째로 종료
    }
}
