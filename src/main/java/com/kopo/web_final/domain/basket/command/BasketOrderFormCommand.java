package com.kopo.web_final.domain.basket.command;

import com.kopo.web_final.Command;
import com.kopo.web_final.domain.basket.dto.ProductItemDto;
import com.kopo.web_final.domain.product.dao.ProductDao;
import com.kopo.web_final.utils.Db;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.util.List;

public class BasketOrderFormCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        req.setCharacterEncoding("UTF-8");
        System.out.println("POST: BasketOrderFormCommand, ID : " + req.getParameter("nbBasket"));

        String nbBasket = req.getParameter("nbBasket");

        String[] productIds = req.getParameterValues("noProduct");

        String[] quantity = req.getParameterValues("quantity");


        int totalPrice = 0;
        int totalDeliveryPrice = 0;
        try(Connection conn = Db.getConnection()){
            // 1. 상품 목록 불러오기
            ProductDao productDao = new ProductDao(conn);
            List<ProductItemDto> productListByBasketIdList = productDao.getProductListByBasketIdList(productIds);

            if(productListByBasketIdList.isEmpty()){
                req.setAttribute("message", "장바구니에 담긴 상품이 없습니다.");
                return "getBasket.do";
            }

            for (int i = 0; i < productListByBasketIdList.size(); i++) {
                ProductItemDto dto = productListByBasketIdList.get(i);
                dto.setQuantity(Integer.parseInt(quantity[i]));

                totalPrice += dto.getQtCustomer() * dto.getQuantity();
                totalDeliveryPrice += dto.getQtDeliveryFee();
            }

            req.setAttribute("productListByBasketIdList", productListByBasketIdList);
            req.setAttribute("totalPrice", totalPrice);
            req.setAttribute("totalDeliveryPrice", totalDeliveryPrice);
        }catch (Exception e) {
            e.printStackTrace();
            return "/error/500.jsp";
        }

        return "/basket/basket_order_form.jsp";
    }
}
