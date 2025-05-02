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
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;

@WebServlet(name = "AdminUpdateController", value = "/admin/member-update")
public class AdminUpdateController extends HttpServlet {

    public AdminUpdateController() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        req.setCharacterEncoding("UTF-8");
        
        // 로그인 검증 및 관리자 권한 확인
        HttpSession session = req.getSession();
        Member loginUser = (Member) session.getAttribute("loginUser");
        
        if (loginUser == null || !"_20".equals(loginUser.getCdUserType())) {
            res.sendRedirect("/member/login.jsp");
            return;
        }
        
        // 변경할 회원 ID 가져오기
        String targetId = req.getParameter("targetId");
        String message;
        
        if (targetId == null || targetId.trim().isEmpty()) {
            message = "변경할 회원 정보가 올바르지 않습니다.";
            res.sendRedirect("/admin/member-list?status=active&message=" + java.net.URLEncoder.encode(message, "UTF-8"));
            return;
        }
        
        // 변경할 상태와 역할 정보 가져오기
        String newStatus = req.getParameter("status_" + targetId);
        String newRole = req.getParameter("role_" + targetId);

        System.out.println("targetId: " + targetId);
        System.out.println("newStatus: " + newStatus);
        System.out.println("newRole: " + newRole);


        if (newStatus == null || newRole == null) {
            message = "변경할 회원 상태 또는 권한 정보가 올바르지 않습니다.";
            res.sendRedirect("/admin/member-list?status=active&message=" + java.net.URLEncoder.encode(message, "UTF-8"));
            return;
        }
        
        try (Connection conn = Db.getConnection()) {
            MemberDao dao = new MemberDao(conn);
            
            // 현재 회원 정보 조회
            Member member = dao.findById(targetId);
            
            if (member == null) {
                message = "존재하지 않는 회원입니다.";
                res.sendRedirect("/admin/member-list?status=active&message=" + java.net.URLEncoder.encode(message, "UTF-8"));
                return;
            }

            // 상태 업데이트 (ST01: 활성, ST03: 정지)
            member.setStStatus(newStatus);
            
            // 권한 업데이트 (_10: 사용자, _20: 관리자)
            member.setCdUserType(newRole);

            System.out.println(member.getStStatus());
            System.out.println(member.getCdUserType());
            
            // 회원 정보 업데이트
            int result = dao.updateMemberAuth(targetId, member);
            
            if (result > 0) {
                message = member.getNmUser() + " 회원의 정보가 성공적으로 변경되었습니다.";
            } else {
                message = "회원 정보 변경에 실패했습니다.";
            }
            
            // 리다이렉트로 변경 (POST -> GET)
            res.sendRedirect("/admin/member-list?status=active&message=" + java.net.URLEncoder.encode(message, "UTF-8"));
            
        } catch (Exception e) {
            e.printStackTrace();
            message = "오류가 발생했습니다: " + e.getMessage();
            res.sendRedirect("/admin/member-list?status=active&message=" + java.net.URLEncoder.encode(message, "UTF-8"));
        }
    }
}