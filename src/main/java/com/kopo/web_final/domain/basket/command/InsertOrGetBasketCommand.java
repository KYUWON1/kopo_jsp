package com.kopo.web_final.domain.basket.command;

import com.kopo.web_final.Command;
import com.kopo.web_final.domain.basket.dao.BasketDao;
import com.kopo.web_final.domain.basket.dao.BasketItemDao;
import com.kopo.web_final.domain.basket.dto.BasketDto;
import com.kopo.web_final.domain.basket.dto.BasketItemDto;
import com.kopo.web_final.domain.member.model.Member;
import com.kopo.web_final.utils.Db;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.util.List;

public class InsertOrGetBasketCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        req.setCharacterEncoding("UTF-8");
        Member loginUser = (Member) req.getSession().getAttribute("loginUser");


        int basketId = 0;
        try(Connection conn = Db.getConnection()){
            // 장바구니 가져오기 및 생성하기
            BasketDao dao = new BasketDao(conn);
            System.out.println("GET: InsertOrGetBasketCommand");
            BasketDto basketDto = dao.getBasket(loginUser.getIdUser());
            if (basketDto == null) {
                System.out.println("POST: InsertOrGetBasketCommand");
                basketId = dao.createBasket(loginUser.getIdUser());
                if (basketId == 0) {
                    return "/error/500.jsp";
                }
                basketDto = dao.getBasket(loginUser.getIdUser()); // 다시 장바구니 정보 로드
            } else {
                basketId = basketDto.getNbBasket();
            }

            // 장바구니 목록 불러오기
            BasketItemDao basketItemDao = new BasketItemDao(conn);
            List<BasketItemDto> basketList = basketItemDao.getBasketList(basketId);

            req.setAttribute("basketTable",basketDto);
            req.setAttribute("basketList",basketList);
            return "/basket/basket_management.jsp";
        }catch(Exception e){
            e.printStackTrace();
            return "/error/500.jsp";
        }

    }
}
