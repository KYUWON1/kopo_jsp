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

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "JoinController", value = "/member/join")
public class JoinController extends HttpServlet {

    public JoinController() {
        super();
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        req.setCharacterEncoding("UTF-8");

        try(Connection conn = Db.getConnection()){
            MemberDao dao = new MemberDao(conn);

            // 이메일 중복 검증
            if(dao.checkEmailExist(req.getParameter("email")) > 0){
                System.out.println("해당 이메일은 존재합니다.");
                req.setAttribute("error", ErrorType.DUPLICATE_ID.getMessage());
                req.getRequestDispatcher("/member/signup.jsp").forward(req, res);
                return;
            }

            int result = dao.insertMember(Member.buildMember(
                    req.getParameter("email"),
                    req.getParameter("userName"),
                    req.getParameter("password"),
                    req.getParameter("phoneNumber")
            ));

            if(result != 1){
                System.out.println("가입 에러 발생.");
                req.setAttribute("error", ErrorType.INTERNAL_ERROR);
                req.getRequestDispatcher("/member/signup.jsp").forward(req, res);
                return;
            }
            req.setAttribute("userName", req.getParameter("userName"));
            req.getRequestDispatcher("/member/signup_success.jsp").forward(req, res);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
