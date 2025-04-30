package com.kopo.web_final.member.controller;

import com.kopo.web_final.exception.MemberException;
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

@WebServlet(name = "LoginController", value = "/member/login")
public class LoginController extends HttpServlet {
    public LoginController() {
        super();
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setCharacterEncoding("UTF-8");

        try(Connection conn = Db.getConnection()) {
            MemberDao dao = new MemberDao(conn);


            Member member = dao.findByEmail(req.getParameter("email"));
            // 계정 존재 X
            if(member == null){
                System.out.println("해당 이메일은 존재하지않습니다.");
                req.setAttribute("error", ErrorType.USER_NOT_FOUND.getMessage());
                req.getRequestDispatcher("/member/login.jsp").forward(req, res);
                return;
            }
            // 패스워드 일치 X
            if(!member.getNmPaswd().equals(req.getParameter("password"))){
                System.out.println("비밀번호가 잘못되었습니다.");
                req.setAttribute("error", ErrorType.INVALID_CREDENTIALS.getMessage());
                req.getRequestDispatcher("/member/login.jsp").forward(req, res);
                return;
            }

            HttpSession session = req.getSession();
            session.setAttribute("loginUser",member);
            req.setAttribute("userName",member.getNmUser());
            req.getRequestDispatcher("/member/login_success.jsp").forward(req,res);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
