package com.kopo.web_final.member.controller;

import com.kopo.web_final.member.dao.MemberDao;
import com.kopo.web_final.member.model.Member;
import com.kopo.web_final.type.UserStatus;
import com.kopo.web_final.utils.Db;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.sql.Connection;

@WebServlet(name = "ApplyController", value = "/admin/member-approval")
public class ApplyController extends HttpServlet {
    public ApplyController() {
        super();
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        req.setCharacterEncoding("UTF-8");
        Member admin = (Member)req.getSession().getAttribute("loginUser");
        String adminId = admin.getIdUser();

        String idUser = req.getParameter("idUser");
        String action = req.getParameter("action"); // "approve" or "withdraw"

        System.out.println("ID : " + idUser );
        System.out.println("ACTION : " + action);
        System.out.println("POST : doApproval");

        try (Connection conn = Db.getConnection()) {
            MemberDao dao = new MemberDao(conn);

            int result = -1;

            if ("approve".equals(action)) {
                result = dao.updateStatusInit(adminId, idUser, UserStatus.ST01); // 가입 승인 → 상태를 활성(A)로
            } else if ("withdraw".equals(action)) {
                result = dao.updateStatus(idUser, UserStatus.ST02); // 탈퇴 승인 → 상태를 탈퇴(D)로
            }

            if (result == 1) {
                // ✅ 성공
                // 원래 페이지로 리디렉션(가입 승인인지 탈퇴 승인인지에 따라 다른 페이지)
                res.sendRedirect(req.getContextPath() + "/admin/member-list?status=" +
                        ("approve".equals(action) ? "apply" : "withdraw") +
                        "&message=" + java.net.URLEncoder.encode("처리가 완료되었습니다.", "UTF-8"));
            } else {
                // ❌ 실패
                res.sendRedirect(req.getContextPath() + "/admin/member-list?status=" +
                        ("approve".equals(action) ? "apply" : "withdraw") +
                        "&message=" + java.net.URLEncoder.encode("처리 중 오류가 발생했습니다.", "UTF-8"));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
