package com.kopo.web_final.basket.command;

import com.kopo.web_final.Command;
import com.kopo.web_final.basket.dao.BasketDao;
import com.kopo.web_final.basket.dao.BasketItemDao;
import com.kopo.web_final.basket.model.BasketItem;
import com.kopo.web_final.member.model.Member;
import com.kopo.web_final.utils.Db;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.time.LocalDate;

public class InsertBasketItemCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        Member loginUser = (Member)req.getSession().getAttribute("loginUser");
        if(loginUser == null){
            req.setAttribute("message", "로그인이 필요한 서비스입니다.");
            return "/member/login.jsp";
        }

        // 상품 정보 가져오기
        String productId = (String)req.getAttribute("productId");
        int quantity = Integer.parseInt((String)req.getAttribute("quantity"));
        int buyPrice = Integer.parseInt((String)req.getAttribute("buyPrice"));
        String deliveryPrice = (String)req.getAttribute("deliveryPrice");

        try(Connection conn = Db.getConnection()){
            // 장바구니 정보 가져오기
            BasketDao basketDao = new BasketDao(conn);
            int basketId = basketDao.getBasketId(loginUser.getIdUser());
            // 장바구니 아직 생성전이라면 생성해서 전달
            if(basketId == -1){
                basketId = basketDao.createBasket(loginUser.getIdUser());
            }
            // 상품 정보 설정하기
            BasketItem basketItem = new BasketItem();
            basketItem.setNbBasket(basketId);
            basketItem.setNoProduct(productId);
            basketItem.setNoUser(loginUser.getIdUser());
            basketItem.setQtBasketItemPrice(buyPrice);
            basketItem.setQtBasketItem(quantity);
            basketItem.setQtBasketItemAmount(buyPrice * quantity);
            basketItem.setNoRegister(loginUser.getNoRegister());
            basketItem.setDaFirstDate(LocalDate.now());

            //장바구니 목록 생성
            BasketItemDao basketItemDao = new BasketItemDao(conn);
            int result = basketItemDao.insertBasketItem(basketItem);
            if(result < 1){
                req.setAttribute("message", "장바구니 추가에 실패했습니다.");
                return "getBasket.do";
            }
            req.setAttribute("message","장바구니 추가에 성공했습니다.");
        }

        return "getBasket.do";
    }
}
