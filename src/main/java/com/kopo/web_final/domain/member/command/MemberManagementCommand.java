package com.kopo.web_final.domain.member.command;

import com.kopo.web_final.Command;
import com.kopo.web_final.domain.member.dao.MemberDao;
import com.kopo.web_final.domain.member.model.Member;
import com.kopo.web_final.type.UserStatus;
import com.kopo.web_final.utils.Db;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.util.List;

public class MemberManagementCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
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
                    return "/admin/user_management.jsp";
                case "apply":
                    return "/admin/approval_apply.jsp";
                case "withdraw":
                    return "/admin/approval_apply.jsp";
                default:
                    return "/admin/user_management.jsp";
            }
        } catch (Exception e) {
            // 예외 처리
            e.printStackTrace();
            req.setAttribute("message", "오류가 발생했습니다");
            return "/admin/user_management.jsp";
        }

    }
}
