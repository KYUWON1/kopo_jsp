package com.kopo.web_final.order.command;

import com.kopo.web_final.Command;
import com.kopo.web_final.member.model.Member;
import com.kopo.web_final.order.dao.OrderDao;
import com.kopo.web_final.order.dto.GetOrderDto;
import com.kopo.web_final.utils.Db;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.util.List;

public class GetOrderCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        req.setCharacterEncoding("UTF-8");

        Member loginUser = (Member) req.getSession().getAttribute("loginUser");
        if(loginUser == null) {
            req.setAttribute("message", "로그인 후 이용 가능합니다.");
            return "/member/login.jsp";
        }

        String idUser = loginUser.getIdUser();

        try(Connection conn = Db.getConnection()){
            OrderDao orderDao = new OrderDao(conn);
            List<GetOrderDto> orderList = orderDao.getOrder(idUser);

            req.setAttribute("orderList",orderList);
        }

        return "/order/order_management.jsp";
    }
}
