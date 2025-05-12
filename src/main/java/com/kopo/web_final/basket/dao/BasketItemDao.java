package com.kopo.web_final.basket.dao;

import com.kopo.web_final.basket.dto.BasketItemDto;
import com.kopo.web_final.basket.model.BasketItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BasketItemDao {
    private Connection conn;

    public BasketItemDao(Connection conn) {
        this.conn = conn;
    }

    public List<BasketItemDto> getBasketList(int basketId) {
        String sql = "SELECT \n" +
                "    c.NB_BASKET_ITEM, \n" +
                "    c.CN_BASKET_ITEM_ORDER, \n" +
                "    c.NO_PRODUCT, \n" +
                "    c.QT_BASKET_ITEM_PRICE, \n" +
                "    c.QT_BASKET_ITEM, \n" +
                "    c.QT_BASKET_ITEM_AMOUNT,\n" +
                "    t.NM_PRODUCT,\n" +
                "    t.QT_STOCK,\n" +
                "    t.ID_FILE\n" +
                "FROM \n" +
                "    TB_BASKET_ITEM c\n" +
                "JOIN tb_product t ON c.NO_PRODUCT = t.NO_PRODUCT \n" +
                "WHERE \n" +
                "    NB_BASKET = ? ";
        try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, basketId);

            ResultSet rs = pstmt.executeQuery();

            List<BasketItemDto> basketItemDtoList = new ArrayList<>();
            while(rs.next()){
                basketItemDtoList.add(
                        new BasketItemDto(
                                rs.getInt("nb_basket_item"),
                                rs.getInt("CN_BASKET_ITEM_ORDER"),
                                rs.getString("no_product"),
                                rs.getInt("qt_basket_item_price"),
                                rs.getInt("qt_basket_item"),
                                rs.getInt("qt_basket_item_amount"),
                                rs.getString("id_file"),
                                rs.getString("NM_PRODUCT"),
                                rs.getInt("QT_STOCK")
                        )
                );
            }

            return basketItemDtoList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int insertBasketItem(BasketItem basketItem) {
        String sql = "INSERT INTO TB_BASKET_ITEM (" +
                "NB_BASKET_ITEM, NB_BASKET, CN_BASKET_ITEM_ORDER, NO_PRODUCT, NO_USER, " +
                "QT_BASKET_ITEM_PRICE, QT_BASKET_ITEM, QT_BASKET_ITEM_AMOUNT, " +
                "NO_REGISTER, DA_FIRST_DATE" +
                ") VALUES (" +
                "SEQ_TB_BASKET_ITEM.NEXTVAL, ?, SEQ_TB_BASKET_ITEM_ORDER.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, basketItem.getNbBasket());
            pstmt.setString(2, basketItem.getNoProduct());
            pstmt.setString(3, basketItem.getNoUser());
            pstmt.setInt(4, basketItem.getQtBasketItemPrice());
            pstmt.setInt(5, basketItem.getQtBasketItem());
            pstmt.setInt(6, basketItem.getQtBasketItemAmount());
            pstmt.setString(7, basketItem.getNoRegister());
            pstmt.setDate(8, java.sql.Date.valueOf(basketItem.getDaFirstDate()));

            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("장바구니 항목 등록 실패", e);
        }
    }

    public int deleteBasketItem(String nbBasketItem) {
        String deleteSql = "DELETE TB_BASKET_ITEM WHERE NB_BASKET_ITEM = ?";
        try(PreparedStatement pstmt = conn.prepareStatement(deleteSql)) {
            pstmt.setString(1, nbBasketItem);

            return pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
