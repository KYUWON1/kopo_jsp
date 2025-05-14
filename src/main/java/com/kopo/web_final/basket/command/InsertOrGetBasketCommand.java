package com.kopo.web_final.basket.command;

import com.kopo.web_final.Command;
import com.kopo.web_final.basket.dao.BasketDao;
import com.kopo.web_final.basket.dao.BasketItemDao;
import com.kopo.web_final.basket.dto.BasketDto;
import com.kopo.web_final.basket.dto.BasketItemDto;
import com.kopo.web_final.basket.model.Basket;
import com.kopo.web_final.member.model.Member;
import com.kopo.web_final.utils.Db;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.util.List;

public class InsertOrGetBasketCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        req.setCharacterEncoding("UTF-8");
        System.out.println("GET: Basket");
        // 현재 장바구니 세션이 있나 조회, 없으면 새로 생성
        Member loginUser = (Member) req.getSession().getAttribute("loginUser");
        if(loginUser == null){
            req.setAttribute("message", "로그인이 필요한 서비스입니다.");
            return "/member/login.jsp";
        }

        int basketId = 0;
        try(Connection conn = Db.getConnection()){
            // 장바구니 가져오기 및 생성하기
            BasketDao dao = new BasketDao(conn);
            System.out.println("장바구니 불러오기");
            BasketDto basketDto = dao.getBasket(loginUser.getIdUser());
            if (basketDto == null) {
                System.out.println("장바구니가 존재하지 않아 새로 생성합니다.");
                basketId = dao.createBasket(loginUser.getIdUser());
                if (basketId == 0) {
                    System.out.println("장바구니 생성 오류");
                    return "";
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
