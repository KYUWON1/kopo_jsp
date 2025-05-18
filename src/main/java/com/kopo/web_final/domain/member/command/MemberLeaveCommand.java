package com.kopo.web_final.domain.member.command;

import com.kopo.web_final.Command;
import com.kopo.web_final.domain.member.dao.MemberDao;
import com.kopo.web_final.domain.member.model.Member;
import com.kopo.web_final.type.UserStatus;
import com.kopo.web_final.utils.Db;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.Connection;

public class MemberLeaveCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        req.setCharacterEncoding("UTF-8");
        String idUser = req.getParameter("idUser");
        String password = req.getParameter("password");


        System.out.println("POST: leave "+"idUser: " + idUser);

        try(Connection conn = Db.getConnection()){
            MemberDao dao = new MemberDao(conn);

            Member member = dao.findById(idUser);
            if(!member.getNmPaswd().equals(password)){
                req.setAttribute("error", "PasswordNotMatch");
                return "/member/leave.jsp";
            }

            int result = dao.updateStatus(idUser, UserStatus.ST03); // ST02: 탈퇴 상태

            if (result != 1) {
                req.setAttribute("error", "DeleteFail");
                return "/member/leave.jsp";
            }
            // 세션 종료
            req.getSession().invalidate();
            // 메인으로 리다이렉트
            req.setAttribute("leave", "success");
        }catch (Exception e) {
            e.printStackTrace();
            return "main.do";
        }

        return "main.do";
    }
}
