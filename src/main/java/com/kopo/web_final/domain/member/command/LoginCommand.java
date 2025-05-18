package com.kopo.web_final.domain.member.command;

import com.kopo.web_final.Command;
import com.kopo.web_final.domain.member.dao.MemberDao;
import com.kopo.web_final.domain.member.model.Member;
import com.kopo.web_final.type.ErrorType;
import com.kopo.web_final.type.UserStatus;
import com.kopo.web_final.utils.Db;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.sql.Connection;


public class LoginCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        res.setCharacterEncoding("UTF-8");
        System.out.println("POST: LoginCommand");

        try(Connection conn = Db.getConnection()) {
            MemberDao dao = new MemberDao(conn);

            Member member = dao.findByEmail(req.getParameter("email"));

            // 계정 존재 X or 활성화 상태 확인
            if(member == null){
                req.setAttribute("error", ErrorType.USER_NOT_FOUND.getMessage());
                return "/member/login.jsp";
            }
            if(!member.getStStatus().equals(UserStatus.ST01.toString())){
                req.setAttribute("error", ErrorType.USER_NOT_FOUND.getMessage());
                return "/member/login.jsp";
            }
            // 패스워드 일치 X
            if(!member.getNmPaswd().equals(req.getParameter("password"))){
                req.setAttribute("error", ErrorType.INVALID_CREDENTIALS.getMessage());
                return "/member/login.jsp";
            }

            HttpSession session = req.getSession();
            member.setNmPaswd(null);
            member.setNmEncPaswd(null);
            session.setAttribute("loginUser",member);
            req.setAttribute("userName",member.getNmUser());

        } catch (Exception e) {
            e.printStackTrace();
            return "/error/500.jsp";
        }

        return "/member/login_success.jsp";
    }
}
