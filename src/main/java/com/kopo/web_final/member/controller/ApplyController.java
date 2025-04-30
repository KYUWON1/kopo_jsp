package com.kopo.web_final.member.controller;

import com.kopo.web_final.member.dao.MemberDao;
import com.kopo.web_final.type.UserStatus;
import com.kopo.web_final.utils.Db;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serial;
import java.sql.Connection;

@WebServlet(name = "ApplyController", value = "/admin/member-approval")
public class ApplyController extends HttpServlet {
    public ApplyController() {
        super();
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        req.setCharacterEncoding("UTF-8");
        String idUser = req.getParameter("idUser");
        String action = req.getParameter("action"); // "approve" or "withdraw"

        try (Connection conn = Db.getConnection()) {
            MemberDao dao = new MemberDao(conn);

            int result = -1;
            if ("approve".equals(action)) {
                result = dao.updateStatus(idUser, UserStatus.ST01); // 가입 승인 → 상태를 활성(A)로
            } else if ("withdraw".equals(action)) {
                result = dao.updateStatus(idUser, UserStatus.ST02); // 탈퇴 승인 → 상태를 탈퇴(D)로
            }
            if (result == 1) {
                // ✅ 성공
                res.sendRedirect(req.getContextPath() + "/admin/approval_apply.jsp?status=" + action + "&result=success");
            } else {
                // ❌ 실패
                res.sendRedirect(req.getContextPath() + "/admin/approval_apply.jsp?status=" + action + "&result=fail");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
