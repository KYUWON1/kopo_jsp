package com.kopo.web_final.basket.command;

import com.kopo.web_final.Command;
import com.kopo.web_final.basket.dao.BasketItemDao;
import com.kopo.web_final.utils.Db;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.Connection;

public class DeleteBasketItemCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        req.setCharacterEncoding("UTF-8");

        String nbBasketItem = req.getParameter("nbBasketItem");
        System.out.println("DELETE: BasketItem: " + nbBasketItem);
        try(Connection conn = Db.getConnection()){
            BasketItemDao basketItemDao = new BasketItemDao(conn);
            int result = basketItemDao.deleteBasketItem(nbBasketItem);
            if(result < 1){
                req.setAttribute("message", "장바구니 삭제에 실패했습니다.");
                return "getBasket.do";
            }
        }
        req.setAttribute("message","장바구니 삭제 성공");
        return "getBasket.do";
    }
}
