package com.kopo.web_final.utils;

import com.kopo.web_final.member.model.Member;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;


public class AuthUtils {
    public static Member checkLogin(HttpServletRequest req, HttpServletResponse res) throws IOException {
        HttpSession session = req.getSession();
        Member loginUser = (Member) session.getAttribute("loginUser");

        return loginUser;
    }

    public static Member checkAdmin(HttpServletRequest req, HttpServletResponse res) throws IOException {
        Member loginUser = checkLogin(req, res);

        if (loginUser == null) {
            return null;
        }

        // 관리자 타입 확인 (_20)
        if (!"_20".equals(loginUser.getCdUserType())) {
            return null;
        }

        return loginUser;
    }
}