package com.kopo.web_final.domain.order.command;

import com.kopo.web_final.Command;
import com.kopo.web_final.domain.member.model.Member;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ProductOrderFormCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        req.setCharacterEncoding("UTF-8");

        Member loginUser = (Member) req.getSession().getAttribute("loginUser");


        String action = req.getParameter("action");

        String productId = req.getParameter("productId");
        String quantity = req.getParameter("quantity");
        String buyPrice = req.getParameter("buyPrice");
        String sellPrice =  req.getParameter("SellPrice");
        String deliveryPrice =  req.getParameter("deliveryPrice");

        System.out.println("POST: ProductOrderCommand");
        System.out.println("PRODUCTID:" + productId);
        System.out.println("USERID:" + loginUser.getIdUser());

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
