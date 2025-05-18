package com.kopo.web_final.order.command;

import com.kopo.web_final.Command;
import com.kopo.web_final.member.model.Member;
import com.kopo.web_final.order.dao.OrderDao;
import com.kopo.web_final.order.dto.GetOrderDto;
import com.kopo.web_final.utils.AuthUtils;
import com.kopo.web_final.utils.Db;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.util.List;

public class GetOrderCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        req.setCharacterEncoding("UTF-8");

        Member loginUser = AuthUtils.checkLogin(req, res);
        if (loginUser == null) {
            req.setAttribute("message", "로그인이 필요한 서비스입니다.");
            res.sendRedirect(req.getContextPath() + "/member/login.jsp");
            return null;
        }

        System.out.println("GET: GetOrderCommand, idUser : " + loginUser.getIdUser());

        String idUser = loginUser.getIdUser();

        try(Connection conn = Db.getConnection()){
            OrderDao orderDao = new OrderDao(conn);
            List<GetOrderDto> orderList = orderDao.getOrderByUserId(idUser);

            req.setAttribute("orderList",orderList);
        }catch (Exception e) {
            e.printStackTrace();
            return "/error/500.jsp";
        }

        return "/order/order_management.jsp";
    }
}
