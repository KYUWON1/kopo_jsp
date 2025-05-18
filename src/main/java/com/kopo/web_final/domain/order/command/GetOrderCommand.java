package com.kopo.web_final.domain.order.command;

import com.kopo.web_final.Command;
import com.kopo.web_final.domain.member.model.Member;
import com.kopo.web_final.domain.order.dao.OrderDao;
import com.kopo.web_final.domain.order.dto.GetOrderDto;
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
