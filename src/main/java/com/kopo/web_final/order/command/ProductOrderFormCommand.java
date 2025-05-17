package com.kopo.web_final.order.command;

import com.kopo.web_final.Command;
import com.kopo.web_final.member.model.Member;
import com.kopo.web_final.order.dao.OrderItemDao;
import com.kopo.web_final.order.model.Order;
import com.kopo.web_final.type.ErrorType;
import com.kopo.web_final.utils.Db;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.Connection;

public class ProductOrderFormCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        req.setCharacterEncoding("UTF-8");

        Member loginUser = (Member)req.getSession().getAttribute("loginUser");
        if(loginUser == null){
            req.setAttribute("message", "로그인이 필요한 서비스입니다.");
            return "/member/login.jsp";
        }

        String action = req.getParameter("action");

        String productId = req.getParameter("productId");
        String quantity = req.getParameter("quantity");
        String buyPrice = req.getParameter("buyPrice");
        String sellPrice =  req.getParameter("SellPrice");
        String deliveryPrice =  req.getParameter("deliveryPrice");

        System.out.println("POST: ProductOrderCommand");
        System.out.println("productId: " + productId + ", quantity: " + quantity + ", buyPrice: " + buyPrice + ", sellPrice: " + sellPrice);

        req.setAttribute("productId", productId);
        req.setAttribute("quantity", quantity);
        req.setAttribute("buyPrice", buyPrice);
        req.setAttribute("sellPrice", sellPrice);
        req.setAttribute("deliveryPrice", deliveryPrice);


        if(action.equals("basket")){
            return "insertBasketItem.do";
        }else if(action.equals("order")){
            return "/order/order_form.jsp";
        }else{
            return "404.do";
        }
    }
}
