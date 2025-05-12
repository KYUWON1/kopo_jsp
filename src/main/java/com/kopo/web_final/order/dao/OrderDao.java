package com.kopo.web_final.order.dao;

import com.kopo.web_final.exception.MemberException;
import com.kopo.web_final.order.model.Order;
import com.kopo.web_final.type.ErrorType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDao {
    private Connection conn;

    public OrderDao(Connection conn) {
        this.conn = conn;
    }

    public String insertOrder(Order order) throws MemberException {
        String idOrder = null;

        try (PreparedStatement pstmt = conn.prepareStatement("SELECT SEQ_TB_ORDER_ID.NEXTVAL FROM DUAL");
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                long sequenceValue = rs.getLong(1);  // 시퀀스 숫자
                idOrder = "ORD_" + String.format("%06d", sequenceValue); // 예: ORD00000001
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("주문 ID 생성 중 오류 발생");
        }

        String sql = "INSERT INTO TB_ORDER (\n" +
                "    ID_ORDER,\n" +
                "    NO_USER,\n" +
                "    QT_ORDER_AMOUNT,\n" +
                "    QT_DELI_MONEY,\n" +
                "    QT_DELI_PERIOD,\n" +
                "    NM_ORDER_PERSON,\n" +
                "    NM_RECEIVER,\n" +
                "    NO_DELIVERY_ZIPNO,\n" +
                "    NM_DELIVERY_ADDRESS,\n" +
                "    NM_RECEIVER_TELNO,\n" +
                "    NM_DELIVERY_SPACE,\n" +
                "    CD_ORDER_TYPE,\n" +
                "    DA_ORDER,\n" +
                "    ST_ORDER,\n" +
                "    ST_PAYMENT,\n" +
                "    NO_REGISTER,\n" +
                "    DA_FIRST_DATE\n" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1,idOrder);
            pstmt.setString(2, order.getNoUser());
            pstmt.setInt(3, order.getQtOrderAmount());
            pstmt.setInt(4, order.getQtDeliMoney());
            pstmt.setInt(5, order.getQtDeliPeriod());
            pstmt.setString(6, order.getNmOrderPerson());
            pstmt.setString(7, order.getNmReceiver());
            pstmt.setString(8, order.getNoDeliveryZipno());
            pstmt.setString(9, order.getNmDeliveryAddress());
            pstmt.setString(10, order.getNmReceiverTelno());
            pstmt.setString(11, order.getNmDeliverySpace());
            pstmt.setString(12, order.getCdOrderType());
            pstmt.setDate(13, java.sql.Date.valueOf(order.getDaOrder())); // LocalDate → java.sql.Date
            pstmt.setString(14, order.getStOrder());
            pstmt.setString(15, order.getStPayment());
            pstmt.setString(16, order.getNoRegister());
            pstmt.setDate(17, java.sql.Date.valueOf(order.getDaFirstDate()));
            int result = pstmt.executeUpdate();
            if(result < 1){
                return null;
            }
            System.out.println(idOrder);
            return idOrder;
        } catch (SQLException e) {
            e.printStackTrace(); // 디버깅을 위해 예외 출력
            throw new MemberException(ErrorType.DB_QUERY_FAIL);
        }
    }
}
