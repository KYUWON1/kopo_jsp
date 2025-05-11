package com.kopo.web_final.member.controller;

import com.kopo.web_final.Command;
import com.kopo.web_final.member.dao.MemberDao;
import com.kopo.web_final.member.model.Member;
import com.kopo.web_final.type.UserStatus;
import com.kopo.web_final.utils.Db;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;

public class MemberApprovalCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        req.setCharacterEncoding("UTF-8");

        Member admin = (Member) req.getSession().getAttribute("loginUser");
        String adminId = admin.getIdUser();

        String idUser = req.getParameter("idUser");
        String action = req.getParameter("action"); // "approve" or "withdraw"

        System.out.println("ID : " + idUser);
        System.out.println("ACTION : " + action);
        System.out.println("POST : doApproval");

        String pageStatus = "apply"; // 기본값

        if ("withdraw".equals(action)) {
            pageStatus = "withdraw";
        }

        try (Connection conn = Db.getConnection()) {
            MemberDao dao = new MemberDao(conn);
            int result = -1;

            if ("approve".equals(action)) {
                result = dao.updateStatusInit(adminId, idUser, UserStatus.ST01); // 승인
            } else if ("withdraw".equals(action)) {
                result = dao.updateStatus(idUser, UserStatus.ST02); // 탈퇴 처리
            }

            if (result == 1) {
                req.setAttribute("message", "처리가 완료되었습니다.");
                req.setAttribute("type", "success");
            } else {
                req.setAttribute("message", "처리 중 오류가 발생했습니다.");
                req.setAttribute("type", "error");
            }

        } catch (Exception e) {
            req.setAttribute("message", "예외 발생: " + e.getMessage());
            req.setAttribute("type", "error");
            e.printStackTrace();
        }

        // forward할 대상 JSP
        req.setAttribute("status", pageStatus);
        return "getMemberList.do?" + pageStatus;
    }
}
