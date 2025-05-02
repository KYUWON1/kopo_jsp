package com.kopo.web_final.member.controller;

import com.kopo.web_final.exception.MemberException;
import com.kopo.web_final.member.dao.MemberDao;
import com.kopo.web_final.member.model.Member;
import com.kopo.web_final.type.ErrorType;
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
import java.sql.SQLException;

@WebServlet(name = "LeaveController", value = "/member/leave")
public class LeaveController extends HttpServlet {
    public LeaveController() {
        super();
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        req.setCharacterEncoding("UTF-8");
        String idUser = req.getParameter("idUser");
        String password = req.getParameter("password");

        System.out.println("POST: leave");
        System.out.println("idUser: " + idUser);
        System.out.println("password: " + password);

        try(Connection conn = Db.getConnection()){
            MemberDao dao = new MemberDao(conn);

            Member member = dao.findById(idUser);
            if(!member.getNmPaswd().equals(password)){
                System.out.println("확인 비밀번호가 일치하지않습니다.");
                res.sendRedirect(req.getContextPath() + "/member/leave.jsp?error=" + ErrorType.INVALID_CREDENTIALS);
                return;
            }

            int result = dao.updateStatus(idUser, UserStatus.ST03); // ST02: 탈퇴 상태

            if (result == 1) {
                // 세션 종료
                req.getSession().invalidate();
                // 메인으로 리다이렉트
                res.sendRedirect(req.getContextPath() + "/index.jsp?leave=success");
            } else {
                res.sendRedirect(req.getContextPath() + "/member/leave.jsp?error=" + ErrorType.INTERNAL_ERROR);
            }

        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
