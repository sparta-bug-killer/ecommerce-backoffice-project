package com.spartabugkiller.ecommercebackofficeproject.global.common;

import com.spartabugkiller.ecommercebackofficeproject.admin.entity.AdminRole;
import com.spartabugkiller.ecommercebackofficeproject.global.exception.ErrorCode;
import com.spartabugkiller.ecommercebackofficeproject.global.exception.UnauthorizedException;
import jakarta.servlet.http.HttpSession;

import java.util.EnumSet;
import java.util.Set;

public class SessionUtils {

    private static final String LOGIN_ADMIN = "LOGIN_ADMIN";

    public static SessionAdmin getLoginAdmin(HttpSession session) {

        // 세션 꺼내기
        SessionAdmin admin = (SessionAdmin) session.getAttribute(LOGIN_ADMIN);

        // null값이면 예외처리
        if (admin == null) {
            throw new UnauthorizedException(ErrorCode.UNAUTHORIZED);
        }
        return admin;
    }

    public static void validateSuperAdmin(HttpSession session) {

        SessionAdmin admin = getLoginAdmin(session);

        // 슈퍼 관리자 예외처리
        if (admin.getRole() != AdminRole.SUPER) {
            throw new UnauthorizedException(ErrorCode.FORBIDDEN);
        }
    }

    public static void validateAdmin(HttpSession session) {
        SessionAdmin admin = getLoginAdmin(session);

        Set<AdminRole> allowedRoles = EnumSet.of(
                AdminRole.SUPER,
                AdminRole.OPERATOR,
                AdminRole.CS
        );

        if (!allowedRoles.contains(admin.getRole())) {
            throw new UnauthorizedException(ErrorCode.FORBIDDEN);
        }
    }
}
