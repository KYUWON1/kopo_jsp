package com.kopo.web_final.order.command.admin;

import com.kopo.web_final.Command;
import com.kopo.web_final.member.model.Member;
import com.kopo.web_final.order.dao.OrderDao;
import com.kopo.web_final.order.dto.GetOrderDto;
import com.kopo.web_final.utils.AuthUtils;
import com.kopo.web_final.utils.Db;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.sql.Connection;
import java.util.List;

public class GetOrderManagementCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        Member member = AuthUtils.checkAdmin(req, res);
        if (member == null) {
            req.setAttribute("message", "로그인이 필요한 서비스입니다.");
            res.sendRedirect(req.getContextPath() + "/member/login.jsp");
            return null;
        }

        // 관리자 타입 확인 (_20)
        if (!"_20".equals(member.getCdUserType())) {
            req.setAttribute("error","관리자만 접근할 수 있습니다.");
            res.sendRedirect(req.getContextPath() + "/member/login.jsp");
            return null;
        }

        try(Connection conn = Db.getConnection()){
            OrderDao orderDao = new OrderDao(conn);
            List<GetOrderDto> order = orderDao.getOrder();

            req.setAttribute("orderList",order);
        }catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("message", "주문 목록을 불러오는 중 오류가 발생했습니다.");
            return "/error/500.jsp";
        }

        return "/admin/order_management.jsp";
    }
}
