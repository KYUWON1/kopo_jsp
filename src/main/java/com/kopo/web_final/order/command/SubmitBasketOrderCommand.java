package com.kopo.web_final.order.command;

import com.kopo.web_final.Command;
import com.kopo.web_final.basket.dao.BasketDao;
import com.kopo.web_final.basket.dao.BasketItemDao;
import com.kopo.web_final.basket.dto.BasketDto;
import com.kopo.web_final.order.dao.OrderDao;
import com.kopo.web_final.order.dao.OrderItemDao;
import com.kopo.web_final.order.dto.GetOrderItemDto;
import com.kopo.web_final.order.model.Order;
import com.kopo.web_final.order.model.OrderItem;
import com.kopo.web_final.product.dao.ProductDao;
import com.kopo.web_final.utils.Db;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SubmitBasketOrderCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        req.setCharacterEncoding("UTF-8");

        // 공통 주문 정보
        String noUser = req.getParameter("noUser");
        String nmUser = req.getParameter("nmUser");
        // 상태값
        String cdOrderType = req.getParameter("cdOrderType");
        String stOrder = req.getParameter("stOrder");
        String stPayment = req.getParameter("stPayment");
        // 금액
        int deliveryFee = Integer.parseInt(req.getParameter("qtOrderItemDeliveryFee"));
        int totalPrice = Integer.parseInt(req.getParameter("qtTotalPrice"));
        // 배송 정보
        String nmOrderPerson = req.getParameter("nmOrderPerson");
        String nmReceiver = req.getParameter("nmReceiver");
        String noDeliveryZipno = req.getParameter("noDeliveryZipno");
        String nmDeliveryAddress = req.getParameter("nmDeliveryAddress");
        String nmDeliveryAddressDetail = req.getParameter("nmDeliveryAddressDetail");
        String nmReceiverTelno = req.getParameter("nmReceiverTelno");
        String nmDeliverySpace = req.getParameter("nmDeliverySpace");
        int qtDeliPeriod = Integer.parseInt(req.getParameter("qtDeliPeriod"));
        // 상품 정보 배열
        String[] productIds = req.getParameterValues("noProduct");
        String[] quantities = req.getParameterValues("qtQuantity");
        String[] qtCustomer = req.getParameterValues("qtCustomer");
        String[] qtDelivery = req.getParameterValues("qtDelivery");


        // 주문 정보 생성
        Order order = new Order();
        order.setNoUser(noUser);
        order.setQtOrderAmount(totalPrice);
        order.setQtDeliMoney(deliveryFee);
        order.setNmOrderPerson(nmOrderPerson);
        order.setNmReceiver(nmReceiver);
        order.setNoDeliveryZipno(noDeliveryZipno);
        order.setNmDeliveryAddress(nmDeliveryAddress);
        order.setNmDeliverySpace(nmDeliverySpace);
        order.setNmReceiverTelno(nmReceiverTelno);
        order.setCdOrderType(cdOrderType);
        order.setStOrder(stOrder);
        order.setStPayment(stPayment);
        order.setQtDeliPeriod(qtDeliPeriod);
        order.setNoRegister(noUser);
        order.setDaFirstDate(LocalDate.now());
        order.setDaOrder(LocalDate.now());

        // 상품 정보 생성
        Connection conn = null;
        try {
            conn = Db.getConnection();
            conn.setAutoCommit(false);
            // 주문 정보 저장
            OrderDao orderDao = new OrderDao(conn);
            String orderId = orderDao.insertOrder(order);

            // 주문 상품 정보 저장
            OrderItemDao orderItemDao = new OrderItemDao(conn);
            List<OrderItem> orderItemList = new ArrayList<>();
            int ItemOrder = 1;
            for (int i = 0; i < productIds.length; i++) {
                OrderItem orderItem = new OrderItem();
                orderItem.setIdOrder(orderId);
                orderItem.setCnOrderItem(ItemOrder++);
                orderItem.setNoProduct(productIds[i]);
                orderItem.setNoUser(noUser);
                orderItem.setQtUnitPrice(Integer.parseInt(qtCustomer[i]));
                orderItem.setQtOrderItem(Integer.parseInt(quantities[i]));
                orderItem.setQtOrderItemAmount(Integer.parseInt(qtCustomer[i]) * Integer.parseInt(quantities[i]));
                orderItem.setQtOrderItemDeliveryFee(Integer.parseInt(qtDelivery[i]));
                orderItem.setStPayment(stPayment);
                orderItem.setNoRegister(noUser);
                orderItem.setDaFirstDate(LocalDate.now());

                orderItemList.add(orderItem);
                int result = orderItemDao.insertOrderItem(orderItem);
                if(result < 1){
                    req.setAttribute("message", "주문 상품 등록에 실패했습니다.");
                    conn.rollback();
                    return "getBasket.do";
                }
            }

            // 장바구니 초기화 => 장바구니 목록 삭제
            BasketDao basketDao = new BasketDao(conn);
            int basketId = basketDao.getBasket(noUser).getNbBasket();

            BasketItemDao basketItemDao = new BasketItemDao(conn);
            int resultBT = basketItemDao.deleteBasketItemByBasketId(basketId);
            if(resultBT < 1){
                req.setAttribute("message", "장바구니 초기화에 실패했습니다.");
                conn.rollback();
                return "getBasket.do";
            }

            // 상품 수량 감소
            ProductDao productDao = new ProductDao(conn);
            int[] resultsPD = productDao.decreaseStocks(productIds, quantities);
            for (int result : resultsPD) {
                if (result < 1) {
                    req.setAttribute("message", "상품 수량 감소에 실패했습니다.");
                    conn.rollback();
                    return "getBasket.do";
                }
            }
            // 모든 트랜잭션 처리 완료
            conn.commit();
            req.setAttribute("message", "장바구니 상품 주문이 완료되었습니다.");
            req.setAttribute("orderId",orderId);
            List<GetOrderItemDto> orderItemListDetail = orderItemDao.getOrderItemListDetail(orderId);
            req.setAttribute("nmUser", nmUser);
            req.setAttribute("totalPrice", totalPrice);
            req.setAttribute("deliveryFee", deliveryFee);
            req.setAttribute("totalPrice", totalPrice + deliveryFee);
            req.setAttribute("orderItemList", orderItemListDetail);
        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (Exception rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            e.printStackTrace();
            req.setAttribute("message", "장바구니 상품 주문에 실패했습니다.");
            return "getBasket.do";
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // 다음 사용을 위해 초기화 (선택적)
                    conn.close();
                } catch (Exception closeEx) {
                    closeEx.printStackTrace();
                }
            }
        }

        return "/order/basket_order_success.jsp";
    }
}
