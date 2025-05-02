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

        if (loginUser == null) {
            res.sendRedirect(req.getContextPath() + "/member/login.jsp?error=need_login");
            return null;
        }

        return loginUser;
    }

    public static Member checkAdmin(HttpServletRequest req, HttpServletResponse res) throws IOException {
        Member loginUser = checkLogin(req, res);

        if (loginUser == null) {
            return null;
        }

        // 관리자 타입 확인 (_20)
        if (!"_20".equals(loginUser.getCdUserType())) {
            res.sendRedirect(req.getContextPath() + "/index.jsp?error=access_denied");
            return null;
        }

        return loginUser;
    }

    public static boolean checkPasswordMatch(String password, String passwordCheck) {
        return password != null && password.equals(passwordCheck);
    }

    public static Member checkSelfAccess(HttpServletRequest req, HttpServletResponse res, String idUser) throws IOException {
        Member loginUser = checkLogin(req, res);

        if (loginUser == null) {
            return null;
        }

        // 관리자이거나 본인인 경우 접근 허용
        if ("_20".equals(loginUser.getCdUserType()) || loginUser.getIdUser().equals(idUser)) {
            return loginUser;
        }

        res.sendRedirect(req.getContextPath() + "/index.jsp?error=access_denied");
        return null;
    }
}