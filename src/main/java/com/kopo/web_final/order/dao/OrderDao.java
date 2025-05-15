package com.kopo.web_final.order.dao;

import com.kopo.web_final.exception.MemberException;
import com.kopo.web_final.order.dto.GetOrderDto;
import com.kopo.web_final.order.model.Order;
import com.kopo.web_final.type.ErrorType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {
    private Connection conn;

    public OrderDao(Connection conn) {
        this.conn = conn;
    }

    public String insertOrder(Order order) throws SQLException {
        String idOrder = null;

        try (PreparedStatement pstmt = conn.prepareStatement("SELECT SEQ_TB_ORDER_ID.NEXTVAL FROM DUAL");
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                long sequenceValue = rs.getLong(1);  // 시퀀스 숫자
                idOrder = "ORD_" + String.format("%06d", sequenceValue); // 예: ORD00000001
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
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
            e.printStackTrace();
            throw e;
        }
    }

    public List<GetOrderDto> getOrder(String idUser) throws SQLException {
        String sqlQuery = "SELECT * FROM TB_ORDER WHERE NO_USER = ? ORDER BY DA_ORDER DESC ";
        try(PreparedStatement pstmt = conn.prepareStatement(sqlQuery)) {
            pstmt.setString(1, idUser);

            ResultSet rs = pstmt.executeQuery();

            List<GetOrderDto> ordetList = new ArrayList<>();
            while(rs.next()){
                GetOrderDto getOrderDto = new GetOrderDto();
                getOrderDto.setIdOrder(rs.getString("ID_ORDER"));
                getOrderDto.setNoUser(rs.getString("NO_USER"));
                getOrderDto.setQtOrderAmount(rs.getInt("QT_ORDER_AMOUNT"));
                getOrderDto.setQtDeliMoney(rs.getInt("QT_DELI_MONEY"));
                getOrderDto.setQtDeliPeriod(rs.getInt("QT_DELI_PERIOD"));
                getOrderDto.setNmOrderPerson(rs.getString("NM_ORDER_PERSON"));
                getOrderDto.setNmReceiver(rs.getString("NM_RECEIVER"));
                getOrderDto.setNoDeliveryZipno(rs.getString("NO_DELIVERY_ZIPNO"));
                getOrderDto.setNmDeliveryAddress(rs.getString("NM_DELIVERY_ADDRESS"));
                getOrderDto.setNmReceiverTelno(rs.getString("NM_RECEIVER_TELNO"));
                getOrderDto.setNmDeliverySpace(rs.getString("NM_DELIVERY_SPACE"));
                getOrderDto.setCdOrderType(rs.getString("CD_ORDER_TYPE"));
                getOrderDto.setStOrder(rs.getString("ST_ORDER"));
                getOrderDto.setStPayment(rs.getString("ST_PAYMENT"));
                getOrderDto.setNoRegister(rs.getString("NO_REGISTER"));
                getOrderDto.setDaOrder(rs.getDate("DA_ORDER").toLocalDate());

                ordetList.add(getOrderDto);
            }
            return ordetList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
