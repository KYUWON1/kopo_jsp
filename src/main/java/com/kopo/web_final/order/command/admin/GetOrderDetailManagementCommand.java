package com.kopo.web_final.order.command.admin;

import com.kopo.web_final.Command;
import com.kopo.web_final.order.dao.OrderItemDao;
import com.kopo.web_final.order.dto.GetOrderItemDto;
import com.kopo.web_final.utils.Db;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.util.List;

public class GetOrderDetailManagementCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        req.setCharacterEncoding("UTF-8");

        String idOrder = req.getParameter("idOrder");
        String totalPrice = req.getParameter("totalPrice");
        String nmReceiver = req.getParameter("nmReceiver");
        String nmDeliveryAddress = req.getParameter("nmDeliveryAddress");
        String daOrder = req.getParameter("daOrder");

        if (idOrder == null || idOrder.isEmpty()) {
            req.setAttribute("message", "주문 ID가 없습니다.");
            return "/admin/order_management.jsp";
        }
        try(Connection conn = Db.getConnection()){
            OrderItemDao orderItemDao = new OrderItemDao(conn);
            List<GetOrderItemDto> orderItemList = orderItemDao.getOrderItemListDetail(idOrder);

            req.setAttribute("orderItemList",orderItemList);
        }catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("message", "주문 상세 정보를 불러오는 중 오류가 발생했습니다.");
            return "/admin/order_management.jsp";
        }
        req.setAttribute("idOrder", idOrder);
        req.setAttribute("totalPrice", totalPrice);
        req.setAttribute("nmReceiver", nmReceiver);
        req.setAttribute("nmDeliveryAddress", nmDeliveryAddress);
        req.setAttribute("daOrder", daOrder);

        return "/admin/order_detail_management.jsp";
    }
}
