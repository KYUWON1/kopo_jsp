package com.kopo.web_final.domain.basket.dao;

import com.kopo.web_final.domain.basket.dto.BasketDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BasketDao {
    private Connection conn;

    public BasketDao(Connection conn) {
        this.conn = conn;
    }

    public BasketDto getBasket(String idUser) throws SQLException {
        String sql = "SELECT NB_BASKET, NO_USER, QT_BASKET_AMOUNT FROM TB_BASKET WHERE NO_USER = ?";
        try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, idUser);

            ResultSet rs = pstmt.executeQuery();

            BasketDto basketDto = null;
            if(rs.next()){
                basketDto = new BasketDto();
                basketDto.setNbBasket(rs.getInt(1));
                basketDto.setNoUser(rs.getString(2));
                basketDto.setQtBasketAmount(rs.getInt(3));

            }
            return basketDto;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public int createBasket(String idUser) throws SQLException {
        String sql = "SELECT SEQ_TB_BASKET_ID.NEXTVAL FROM DUAL";

        int seqVal = 0;
        try(PreparedStatement pstmt1 = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt1.executeQuery();
            if(rs.next()){
                seqVal = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        String sqlQuery = "INSERT INTO TB_BASKET (NB_BASKET, NO_USER,QT_BASKET_AMOUNT) VALUES (? ,? ,?)";
        try(PreparedStatement pstmt = conn.prepareStatement(sqlQuery)) {
            pstmt.setInt(1, seqVal);
            pstmt.setString(2, idUser);
            pstmt.setInt(3, 0);

            int result = pstmt.executeUpdate();

            if(result < 1){
                return -1;
            }

            return seqVal;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public int getBasketId(String idUser) throws SQLException {
        String sql = "SELECT NB_BASKET FROM TB_BASKET WHERE NO_USER = ?";
        try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, idUser);

            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                int nbBasket = rs.getInt(1);

                return nbBasket;
            }
            return -1;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
