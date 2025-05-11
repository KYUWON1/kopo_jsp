package com.kopo.web_final.member.controller;

import com.kopo.web_final.Command;
import com.kopo.web_final.member.dao.MemberDao;
import com.kopo.web_final.member.model.Member;
import com.kopo.web_final.utils.Db;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.sql.Connection;

public class MemberStatusUpdateCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        req.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession();
        Member loginUser = (Member) session.getAttribute("loginUser");

        if (loginUser == null || !"_20".equals(loginUser.getCdUserType())) {
            return "/member/login.jsp";
        }

        String targetId = req.getParameter("targetId");
        if (targetId == null || targetId.trim().isEmpty()) {
            req.setAttribute("message", "변경할 회원 정보가 올바르지 않습니다.");
            req.setAttribute("type", "error");
            return "getMemberList.do?status=active";
        }

        String newStatus = req.getParameter("status_" + targetId);
        String newRole = req.getParameter("role_" + targetId);

        if (newStatus == null || newRole == null) {
            req.setAttribute("message", "변경할 회원 상태 또는 권한 정보가 올바르지 않습니다.");
            req.setAttribute("type", "error");
            return "getMemberList.do?status=active";
        }

        try (Connection conn = Db.getConnection()) {
            MemberDao dao = new MemberDao(conn);
            Member member = dao.findById(targetId);

            if (member == null) {
                req.setAttribute("message", "존재하지 않는 회원입니다.");
                req.setAttribute("type", "error");
                return "getMemberList.do?status=active";
            }

            member.setStStatus(newStatus);
            member.setCdUserType(newRole);

            int result = dao.updateMemberAuth(targetId, member);

            if (result > 0) {
                req.setAttribute("message", member.getNmUser() + " 회원의 정보가 성공적으로 변경되었습니다.");
                req.setAttribute("type", "success");
            } else {
                req.setAttribute("message", "회원 정보 변경에 실패했습니다.");
                req.setAttribute("type", "error");
            }

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("message", "오류가 발생했습니다: " + e.getMessage());
            req.setAttribute("type", "error");
        }

        return "getMemberList.do?status=active"; // 실제 뷰 파일 경로에 맞춰 조정
    }
}
