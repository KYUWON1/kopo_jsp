package com.kopo.web_final.domain.member.command;

import com.kopo.web_final.Command;
import com.kopo.web_final.domain.member.dao.MemberDao;
import com.kopo.web_final.domain.member.model.Member;
import com.kopo.web_final.type.ErrorType;
import com.kopo.web_final.utils.Db;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.Connection;

public class MemberInfoAuthCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        req.setCharacterEncoding("UTF-8");
        System.out.println("POST: MemberInfoAuthCommand, ID : " + req.getParameter("idUser"));

        String idUser = req.getParameter("idUser");
        String password = req.getParameter("password");

        try(Connection conn = Db.getConnection()) {
            MemberDao dao = new MemberDao(conn);

            Member member = dao.findById(idUser);

            if(!member.getNmPaswd().equals(password)){
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
