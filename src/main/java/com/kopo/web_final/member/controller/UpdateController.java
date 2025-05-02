package com.kopo.web_final.member.controller;

import com.kopo.web_final.member.dao.MemberDao;
import com.kopo.web_final.member.model.Member;
import com.kopo.web_final.type.ErrorType;
import com.kopo.web_final.utils.Db;
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

@WebServlet(name = "UpdateController", value = "/member/update")
public class UpdateController extends HttpServlet {

    public UpdateController() {
        super();
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        req.setCharacterEncoding("UTF-8");

        try (Connection conn = Db.getConnection()) {
            MemberDao dao = new MemberDao(conn);

            String idUser = req.getParameter("idUser");
            String email = req.getParameter("email");
            String bfEmail = req.getParameter("bfEmail");
            String username = req.getParameter("username");
            String phoneNumber = req.getParameter("phoneNumber");



            // 이메일이 변경되었을 때만 중복 검증 수행
            if (!email.equals(bfEmail) && dao.checkEmailExist(email) > 0) {
                System.out.println("해당 이메일은 이미 존재합니다.");
                req.setAttribute("error", ErrorType.DUPLICATE_ID.getMessage());
                req.getRequestDispatcher("/member/info.jsp").forward(req, res);
                return;
            }

            HttpSession session = req.getSession();
            Member loginUser = (Member) session.getAttribute("loginUser");

            // 값 업데이트
            loginUser.setNmEmail(email);
            loginUser.setNmUser(req.getParameter("username")); // 이름
            loginUser.setNoMobile(req.getParameter("phoneNumber"));

            int result = dao.updateMember(idUser, loginUser);

            if (result > 0) {
                // 세션의 유저 정보도 업데이트
                loginUser.setNmEmail(email);
                loginUser.setNmUser(username);
                loginUser.setNoMobile(phoneNumber);
                session.setAttribute("loginUser", loginUser);

                String encodedMessage = URLEncoder.encode("회원 정보가 성공적으로 수정되었습니다.", "UTF-8");
                res.sendRedirect("/member/info.jsp?message=" + encodedMessage);
            } else {
                String encodedError = URLEncoder.encode("회원 정보 수정에 실패했습니다.", "UTF-8");
                req.setAttribute("error",ErrorType.INTERNAL_ERROR.getMessage());
                res.sendRedirect("/member/info.jsp");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
