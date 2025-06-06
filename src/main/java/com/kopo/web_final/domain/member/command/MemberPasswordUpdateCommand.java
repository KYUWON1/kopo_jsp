package com.kopo.web_final.domain.member.command;

import com.kopo.web_final.Command;
import com.kopo.web_final.domain.member.dao.MemberDao;
import com.kopo.web_final.domain.member.model.Member;
import com.kopo.web_final.utils.Db;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.Connection;

public class MemberPasswordUpdateCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        req.setCharacterEncoding("UTF-8");
        System.out.println("POST: change-password");
        // 로그인 사용자 확인
        Member loginUser = (Member) req.getSession().getAttribute("loginUser");

        String idUser = req.getParameter("idUser");
        String currentPassword = req.getParameter("currentPassword");
        String newPassword = req.getParameter("newPassword");
        String confirmPassword = req.getParameter("confirmPassword");

        // 본인 확인
        if (!loginUser.getIdUser().equals(idUser)) {
            req.setAttribute("message","비정상적인 접근입니다.");
            req.setAttribute("type", "error");
        }

        try (Connection conn = Db.getConnection()) {
            MemberDao dao = new MemberDao(conn);
            Member member = dao.findById(idUser);

            // 현재 비밀번호 확인
            if (!member.getNmPaswd().equals(currentPassword)) {
                req.setAttribute("message","현재 비밀번호가 일치하지 않습니다.");
                req.setAttribute("type", "error");
                return "/member/info.jsp";
            }

            // 새 비밀번호 확인
            if (!newPassword.equals(confirmPassword)) {
                req.setAttribute("message","새 비밀번호가 일치하지 않습니다.");
                req.setAttribute("type", "error");
                return "/member/info.jsp";
            }

            // 새 비밀번호가 현재 비밀번호와 같은지 확인
            if (currentPassword.equals(newPassword)) {
                req.setAttribute("message","새 비밀번호가 현재 비밀번호와 같습니다.");
                req.setAttribute("type", "error");
                return "/member/info.jsp";
            }

            // 비밀번호 업데이트
            member.setNmPaswd(newPassword);
            member.setNmEncPaswd(newPassword);
            int result = dao.updateMemberPassword(idUser, member);

            if (result > 0) {
                // 세션의 유저 정보도 업데이트
                loginUser.setNmPaswd(newPassword);
                loginUser.setNmEncPaswd(newPassword);
                req.getSession().setAttribute("loginUser", loginUser);

                req.setAttribute("message","비밀번호가 성공적으로 변경되었습니다.");
                req.setAttribute("type", "success");
            } else {
                req.setAttribute("message","비밀번호 변경에 실패했습니다.");
                req.setAttribute("type", "error");
                return "/member/info.jsp";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "/error/500.jsp";
        }

        return "/member/info.jsp";
    }
}

