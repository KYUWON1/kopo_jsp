package com.kopo.web_final.order.command;

import com.kopo.web_final.Command;
import com.kopo.web_final.order.dao.OrderDao;
import com.kopo.web_final.order.dao.OrderItemDao;
import com.kopo.web_final.order.model.Order;
import com.kopo.web_final.order.model.OrderItem;
import com.kopo.web_final.utils.Db;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.UUID;

public class OrderInsertCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");

        String noUser = request.getParameter("noUser");
        String noProduct = request.getParameter("noProduct");

        int qtOrderItem = Integer.parseInt(request.getParameter("qtOrderItem"));
        int qtOrderAmount = Integer.parseInt(request.getParameter("qtOrderAmount"));
        int qtUnitPrice = Integer.parseInt(request.getParameter("qtUnitPrice"));
        int qtOrderItemDeliveryFee = Integer.parseInt(request.getParameter("qtOrderItemDeliveryFee"));

        String nmOrderPerson = request.getParameter("nmOrderPerson");
        String nmReceiver = request.getParameter("nmReceiver");
        String noDeliveryZipno = request.getParameter("noDeliveryZipno");
        String nmDeliveryAddress = request.getParameter("nmDeliveryAddress");
        String nmDeliveryAddressDetail = request.getParameter("nmDeliveryAddressDetail");
        String nmReceiverTelno = request.getParameter("nmReceiverTelno");
        String nmDeliverySpace = request.getParameter("nmDeliverySpace");
        int qtDeliPeriod = Integer.parseInt(request.getParameter("qtDeliPeriod"));

        String cdOrderType = request.getParameter("cdOrderType");
        String stOrder = request.getParameter("stOrder");
        String stPayment = request.getParameter("stPayment");
        String noRegister = request.getParameter("noRegister");

        LocalDate now = LocalDate.now();

        Connection conn = null;

        try {
            conn = Db.getConnection();
            conn.setAutoCommit(false);

            // 주문 정보 생성
            Order order = new Order();
            order.setNoUser(noUser);
            order.setQtOrderAmount(qtOrderAmount);
            order.setQtDeliMoney(qtOrderItemDeliveryFee);
            order.setQtDeliPeriod(qtDeliPeriod);
            order.setNmOrderPerson(nmOrderPerson);
            order.setNmReceiver(nmReceiver);
            order.setNoDeliveryZipno(noDeliveryZipno);
            order.setNmDeliveryAddress(nmDeliveryAddress + " " + nmDeliveryAddressDetail);
            order.setNmReceiverTelno(nmReceiverTelno);
            order.setNmDeliverySpace(nmDeliverySpace);
            order.setCdOrderType(cdOrderType);
            order.setDaOrder(now);
            order.setStOrder(stOrder);
            order.setStPayment(stPayment);
            order.setNoRegister(noRegister);
            order.setDaFirstDate(now);

            OrderDao orderDao = new OrderDao(conn);
            String orderId = orderDao.insertOrder(order);
            if (orderId == null) {
                conn.rollback();
                request.setAttribute("message", "주문 등록에 실패했습니다.");
                request.setAttribute("type", "error");
                return "productDetail.do?productId=" + noProduct;
            }

            // 주문 품목 정보 생성
            OrderItem orderItem = new OrderItem();
            orderItem.setIdOrder(orderId);
            orderItem.setCnOrderItem(1); // 단일 상품일 경우 항상 1
            orderItem.setNoProduct(noProduct);
            orderItem.setNoUser(noUser);
            orderItem.setQtUnitPrice(qtUnitPrice);
            orderItem.setQtOrderItem(qtOrderItem);
            orderItem.setQtOrderItemAmount(qtOrderItem * qtUnitPrice);
            orderItem.setQtOrderItemDeliveryFee(qtOrderItemDeliveryFee);
            orderItem.setStPayment(stPayment);
            orderItem.setNoRegister(noRegister);
            orderItem.setDaFirstDate(now);

            OrderItemDao orderItemDao = new OrderItemDao(conn);
            int orderItemResult = orderItemDao.insertOrderItem(orderItem);
            if (orderItemResult < 1) {
                conn.rollback();
                request.setAttribute("message", "주문 품목 등록에 실패했습니다.");
                request.setAttribute("type", "error");
                return "productDetail.do?productId=" + noProduct;
            }

            conn.commit();
            request.setAttribute("message", "주문이 정상적으로 완료되었습니다.");
            request.setAttribute("type", "success");
            request.setAttribute("orderId", orderId);
            request.setAttribute("orderAmount", String.valueOf(qtOrderAmount));
            request.setAttribute("deliveryFee", String.valueOf(qtOrderItemDeliveryFee));
            return "/order/order_success.jsp";

        } catch (Exception e) {
            if (conn != null) conn.rollback();
            request.setAttribute("message", "주문 처리 중 오류가 발생했습니다.");
            request.setAttribute("type", "error");
            e.printStackTrace();
            return "productDetail.do?productId=" + noProduct;
        } finally {
            if (conn != null) try { conn.setAutoCommit(true); conn.close(); } catch (Exception ignored) {}
        }
    }
}
