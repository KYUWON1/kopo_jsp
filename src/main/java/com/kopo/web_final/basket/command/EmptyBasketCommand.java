package com.kopo.web_final.basket.command;

import com.kopo.web_final.Command;
import com.kopo.web_final.basket.dao.BasketItemDao;
import com.kopo.web_final.utils.Db;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.Connection;

public class EmptyBasketCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        req.setCharacterEncoding("UTF-8");

        String nbBasket = req.getParameter("nbBasket");

        try(Connection conn = Db.getConnection()){
            BasketItemDao basketItemDao = new BasketItemDao(conn);
            int result = basketItemDao.deleteBasketItemByBasketId(Integer.parseInt(nbBasket));
            if(result < 1){
                req.setAttribute("message", "장바구니 비우기 실패");
                return "getBasket.do";
            }

            req.setAttribute("message", "장바구니 비우기 성공");
        }catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("message", "장바구니 비우기 실패");
            return "getBasket.do";
        }

        return "getBasket.do";
    }
}
