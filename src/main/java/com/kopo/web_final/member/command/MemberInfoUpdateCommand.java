package com.kopo.web_final.member.command;

import com.kopo.web_final.Command;
import com.kopo.web_final.member.dao.MemberDao;
import com.kopo.web_final.member.model.Member;
import com.kopo.web_final.type.ErrorType;
import com.kopo.web_final.utils.Db;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.sql.Connection;

public class MemberInfoUpdateCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        req.setCharacterEncoding("UTF-8");
        System.out.println("POST: MemberInfoUpdateCommand, ID : " + req.getParameter("idUser"));

        try (Connection conn = Db.getConnection()) {
            MemberDao dao = new MemberDao(conn);

            String idUser = req.getParameter("idUser");
            String email = req.getParameter("email");
            String bfEmail = req.getParameter("bfEmail");
            String username = req.getParameter("username");
            String phoneNumber = req.getParameter("phoneNumber");



            // 이메일이 변경되었을 때만 중복 검증 수행
            if (!email.equals(bfEmail) && dao.checkEmailExist(email) > 0) {
                req.setAttribute("message", ErrorType.DUPLICATE_ID.getMessage());
                req.setAttribute("type", "error");
                return "/member/info.jsp";
            }

            HttpSession session = req.getSession();
            Member loginUser = (Member) session.getAttribute("loginUser");

            // 값 업데이트
            loginUser.setNmEmail(email);
            loginUser.setNmUser(req.getParameter("username")); // 이름
            loginUser.setNoMobile(req.getParameter("phoneNumber"));

            int result = dao.updateMember(idUser, loginUser);

            if (result > 0) {
                // 세션의 유저 정보도 업데이트
                loginUser.setNmEmail(email);
                loginUser.setNmUser(username);
                loginUser.setNoMobile(phoneNumber);
                session.setAttribute("loginUser", loginUser);
                req.setAttribute("message", "회원 정보가 성공적으로 수정되었습니다.");
                req.setAttribute("type", "success");
            } else {
                req.setAttribute("error",ErrorType.INTERNAL_ERROR.getMessage());
                req.setAttribute("message", "회원 정보 수정에 실패했습니다.");
                req.setAttribute("type", "error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "/error/500.jsp";
        }

        return "/member/info.jsp";
    }
}
