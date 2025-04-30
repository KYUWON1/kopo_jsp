package com.kopo.web_final.member.controller;

import com.kopo.web_final.member.dao.MemberDao;
import com.kopo.web_final.member.model.Member;
import com.kopo.web_final.type.UserStatus;
import com.kopo.web_final.utils.Db;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serial;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "MemberListController", value = "/admin/member-list")
public class MemberListController extends HttpServlet {

    public MemberListController() {
        super();
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        req.setCharacterEncoding("UTF-8");
        String status = req.getParameter("status");
        String message = req.getParameter("message");

        // 메시지가 있으면 request에 설정
        if (message != null && !message.isEmpty()) {
            req.setAttribute("message", message);
        }

        UserStatus uStatus;

        // status가 null인 경우 기본값으로 "active" 설정
        if (status == null) {
            status = "active";
        }

        // switch 문 사용 수정
        switch (status) {
            case "active":
                uStatus = UserStatus.ST01;
                break;
            case "apply":
                uStatus = UserStatus.ST00;
                break;
            case "withdraw":
                uStatus = UserStatus.ST03;
                break;
            default:
                uStatus = UserStatus.ST01;
                break;
        }

        try (Connection conn = Db.getConnection()) {
            MemberDao dao = new MemberDao(conn);
            List<Member> memberList = dao.getActiveMemberList(uStatus);
            req.setAttribute("memberList", memberList);

            // 페이지 포워딩
            switch (status) {
                case "active":
                    req.getRequestDispatcher("/admin/user_management.jsp").forward(req, res);
                    break;
                case "apply":
                    req.getRequestDispatcher("/admin/approval_apply.jsp").forward(req, res);
                    break;
                case "withdraw":
                    req.getRequestDispatcher("/admin/approval_apply.jsp").forward(req, res);
                    break;
                default:
                    req.getRequestDispatcher("/admin/user_management.jsp").forward(req, res);
                    break;
            }
        } catch (Exception e) {
            // 예외 처리
            e.printStackTrace();
            req.setAttribute("message", "오류가 발생했습니다: " + e.getMessage());
            try {
                req.getRequestDispatcher("/admin/user_management.jsp").forward(req, res);
            } catch (ServletException se) {
                se.printStackTrace();
            }
        }
    }
}
