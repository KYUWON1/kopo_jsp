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
            String newPassword = req.getParameter("password");
            if (newPassword != null && !newPassword.trim().isEmpty()) {
                loginUser.setNmPaswd(newPassword); // 새 비밀번호 입력한 경우만 반영
            }
            loginUser.setNoMobile(req.getParameter("phoneNumber"));

            int result = dao.updateMember(idUser, loginUser);

            if (result != 1) {
                System.out.println("회원 정보 수정 실패.");
                req.setAttribute("error", ErrorType.UPDATE_FAIL.getMessage());
                req.getRequestDispatcher("/member/info.jsp").forward(req, res);
                return;
            }

            // 세션 업데이트
            session.setAttribute("loginUser", loginUser);

            // 성공 후 다시 정보 페이지로 이동
            req.setAttribute("success", true); // 성공 표시
            req.getRequestDispatcher("/member/info.jsp").forward(req, res);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
