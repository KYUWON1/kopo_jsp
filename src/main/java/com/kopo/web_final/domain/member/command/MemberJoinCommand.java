package com.kopo.web_final.domain.member.command;

import com.kopo.web_final.Command;
import com.kopo.web_final.domain.member.dao.MemberDao;
import com.kopo.web_final.domain.member.model.Member;
import com.kopo.web_final.type.ErrorType;
import com.kopo.web_final.utils.Db;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.Connection;

public class MemberJoinCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        req.setCharacterEncoding("UTF-8");
        System.out.println("POST: MemberJoinCommand");

        try(Connection conn = Db.getConnection()){
            MemberDao dao = new MemberDao(conn);

            // 이메일 중복 검증
            if(dao.checkEmailExist(req.getParameter("email")) > 0){
                req.setAttribute("error", ErrorType.DUPLICATE_ID.getMessage());
               return "/member/signup.jsp";
            }

            int result = dao.insertMember(Member.buildMember(
                    req.getParameter("email"),
                    req.getParameter("username"),
                    req.getParameter("password"),
                    req.getParameter("phoneNumber")
            ));

            if(result != 1){
                req.setAttribute("error", ErrorType.INTERNAL_ERROR.getMessage());
                return "/member/signup.jsp";
            }
            req.setAttribute("userName", req.getParameter("userName"));
          } catch (Exception e) {
            e.printStackTrace();
            return "/error/500.jsp";
        }

        return "/member/signup_success.jsp";
    }
}
