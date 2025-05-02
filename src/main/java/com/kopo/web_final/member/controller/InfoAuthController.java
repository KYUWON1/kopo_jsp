package com.kopo.web_final.member.controller;

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
import java.sql.SQLException;

@WebServlet(name = "InfoAuthController", value = "/member/info-auth")
public class InfoAuthController extends HttpServlet {
    public InfoAuthController() {
        super();
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        req.setCharacterEncoding("UTF-8");

        String idUser = req.getParameter("idUser");
        String password = req.getParameter("password");

        try(Connection conn = Db.getConnection()) {
            MemberDao dao = new MemberDao(conn);

            Member member = dao.findById(idUser);

            if(!member.getNmPaswd().equals(password)){
                System.out.println("비밀번호가 잘못되었습니다.");
                req.setAttribute("error", ErrorType.INVALID_CREDENTIALS.getMessage());
                req.getRequestDispatcher("/member/info_auth.jsp").forward(req, res);
            }

            res.sendRedirect(req.getContextPath() + "/member/info.jsp?auth=success");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
