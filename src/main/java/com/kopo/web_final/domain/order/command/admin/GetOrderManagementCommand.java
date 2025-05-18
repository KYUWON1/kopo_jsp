package com.kopo.web_final.domain.order.command.admin;

import com.kopo.web_final.Command;
import com.kopo.web_final.domain.order.dao.OrderDao;
import com.kopo.web_final.domain.order.dto.GetOrderDto;
import com.kopo.web_final.utils.Db;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.util.List;

public class GetOrderManagementCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

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
