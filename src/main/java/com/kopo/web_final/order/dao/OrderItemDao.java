package com.kopo.web_final.order.dao;

import com.kopo.web_final.exception.MemberException;
import com.kopo.web_final.order.model.OrderItem;
import com.kopo.web_final.type.ErrorType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderItemDao {
    private Connection conn;

    public OrderItemDao(Connection conn) {
        this.conn = conn;
    }

    public int insertOrderItem(OrderItem orderItem) throws MemberException {
        String idOrderItem = null;
        try(PreparedStatement pstmt = conn.prepareStatement("SELECT SEQ_TB_ORDER_ITEM_ID.NEXTVAL FROM DUAL")){
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()){
                long sequenceValue = rs.getLong(1);
                idOrderItem = "ORIT_" + String.format("%06d", sequenceValue); // 예: ORD00000001
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        String sql = "INSERT INTO TB_ORDER_ITEM (\n" +
                "    ID_ORDER_ITEM,\n" +
                "    ID_ORDER,\n" +
                "    CN_ORDER_ITEM,\n" +
                "    NO_PRODUCT,\n" +
                "    NO_USER,\n" +
                "    QT_UNIT_PRICE,\n" +
                "    QT_ORDER_ITEM,\n" +
                "    QT_ORDER_ITEM_AMOUNT,\n" +
                "    QT_ORDER_ITEM_DELIVERY_FEE,\n" +
                "    ST_PAYMENT,\n" +
                "    NO_REGISTER,\n" +
                "    DA_FIRST_DATE\n" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, idOrderItem);
            System.out.println(orderItem.getIdOrder());
            pstmt.setString(2, orderItem.getIdOrder());
            pstmt.setInt(3, orderItem.getCnOrderItem());
            pstmt.setString(4, orderItem.getNoProduct());
            pstmt.setString(5, orderItem.getNoUser());
            pstmt.setInt(6, orderItem.getQtUnitPrice());
            pstmt.setInt(7, orderItem.getQtOrderItem());
            pstmt.setInt(8, orderItem.getQtOrderItemAmount());
            pstmt.setInt(9, orderItem.getQtOrderItemDeliveryFee());
            pstmt.setString(10, orderItem.getStPayment());
            pstmt.setString(11, orderItem.getNoRegister());
            pstmt.setDate(12, java.sql.Date.valueOf(orderItem.getDaFirstDate()));

            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // 디버깅을 위해 예외 출력
            throw new MemberException(ErrorType.DB_QUERY_FAIL);
        }
    }
}
