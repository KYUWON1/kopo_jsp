package com.kopo.web_final.member.command;

import com.kopo.web_final.Command;
import com.kopo.web_final.member.dao.MemberDao;
import com.kopo.web_final.member.model.Member;
import com.kopo.web_final.type.ErrorType;
import com.kopo.web_final.utils.Db;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.SQLException;

public class MemberInfoAuthCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        req.setCharacterEncoding("UTF-8");

        String idUser = req.getParameter("idUser");
        String password = req.getParameter("password");

        try(Connection conn = Db.getConnection()) {
            MemberDao dao = new MemberDao(conn);

            Member member = dao.findById(idUser);

            if(!member.getNmPaswd().equals(password)){
                System.out.println("비밀번호가 잘못되었습니다.");
                req.setAttribute("error", ErrorType.INVALID_CREDENTIALS.getMessage());
                return "/member/info_auth.jsp";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "/error/500.jsp";
        }

        return "/member/info.jsp?auth=success";
    }
}
