package com.kopo.web_final.member.controller;

import com.kopo.web_final.member.dao.MemberDao;
import com.kopo.web_final.member.model.Member;
import com.kopo.web_final.utils.Db;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serial;
import java.net.URLEncoder;
import java.sql.Connection;

@WebServlet(name = "ChangePasswordController", value = "/member/change-password")
public class ChangePasswordController extends HttpServlet {

    public ChangePasswordController() {
        super();
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        System.out.println("POST: change-password");
        // 로그인 사용자 확인
        HttpSession session = req.getSession();
        Member loginUser = (Member) session.getAttribute("loginUser");

        if (loginUser == null) {
            res.sendRedirect("/member/login.jsp");
            return;
        }

        String idUser = req.getParameter("idUser");
        String currentPassword = req.getParameter("currentPassword");
        String newPassword = req.getParameter("newPassword");
        String confirmPassword = req.getParameter("confirmPassword");

        // 본인 확인
        if (!loginUser.getIdUser().equals(idUser)) {
            String encodedError = URLEncoder.encode("비정상적인 접근입니다.", "UTF-8");
            res.sendRedirect("/member/info.jsp?error=" + encodedError);
            return;
        }

        try (Connection conn = Db.getConnection()) {
            MemberDao dao = new MemberDao(conn);
            Member member = dao.findById(idUser);

            // 현재 비밀번호 확인
            if (!member.getNmPaswd().equals(currentPassword)) {
                String encodedError = URLEncoder.encode("현재 비밀번호가 일치하지 않습니다.", "UTF-8");
                res.sendRedirect("/member/info.jsp?passwordError=" + encodedError);
                return;
            }

            // 새 비밀번호 확인
            if (!newPassword.equals(confirmPassword)) {
                String encodedError = URLEncoder.encode("새 비밀번호가 일치하지 않습니다.", "UTF-8");
                res.sendRedirect("/member/info.jsp?passwordError=" + encodedError);
                return;
            }

            // 새 비밀번호가 현재 비밀번호와 같은지 확인
            if (currentPassword.equals(newPassword)) {
                String encodedError = URLEncoder.encode("새 비밀번호가 현재 비밀번호와 같습니다.", "UTF-8");
                res.sendRedirect("/member/info.jsp?passwordError=" + encodedError);
                return;
            }

            // 비밀번호 업데이트
            member.setNmPaswd(newPassword);
            member.setNmEncPaswd(newPassword);
            int result = dao.updateMemberPassword(idUser, member);

            if (result > 0) {
                // 세션의 유저 정보도 업데이트
                loginUser.setNmPaswd(newPassword);
                loginUser.setNmEncPaswd(newPassword);
                session.setAttribute("loginUser", loginUser);

                String encodedMessage = URLEncoder.encode("비밀번호가 성공적으로 변경되었습니다.", "UTF-8");
                res.sendRedirect("/member/info.jsp?message=" + encodedMessage);
            } else {
                String encodedError = URLEncoder.encode("비밀번호 변경에 실패했습니다.", "UTF-8");
                res.sendRedirect("/member/info.jsp?error=" + encodedError);
            }
        } catch (Exception e) {
            String encodedError = URLEncoder.encode("서버 오류가 발생했습니다: " + e.getMessage(), "UTF-8");
            res.sendRedirect("/member/info.jsp?error=" + encodedError);
        }
    }
}

