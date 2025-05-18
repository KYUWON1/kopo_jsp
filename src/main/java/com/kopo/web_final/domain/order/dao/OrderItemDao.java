package com.kopo.web_final.domain.order.dao;

import com.kopo.web_final.domain.order.dto.GetOrderItemDto;
import com.kopo.web_final.domain.order.model.OrderItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDao {
    private Connection conn;

    public OrderItemDao(Connection conn) {
        this.conn = conn;
    }

    public int insertOrderItem(OrderItem orderItem) throws SQLException {
        String idOrderItem = null;
        try(PreparedStatement pstmt = conn.prepareStatement("SELECT SEQ_TB_ORDER_ITEM_ID.NEXTVAL FROM DUAL")){
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()){
                long sequenceValue = rs.getLong(1);
                idOrderItem = "ORIT_" + String.format("%06d", sequenceValue); // 예: ORD00000001
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
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
            e.printStackTrace();
            throw e;
        }
    }

    public List<GetOrderItemDto> getOrderItemList(String idOrder) throws SQLException {
        String sqlQuery = "select o.*,p.nm_product,p.qt_customer,p.qt_delivery_fee,p.id_file from tb_order_item o\n" +
                "join tb_product p on o.no_product = p.no_product\n" +
                "where o.id_order = ? order by cn_order_item asc";

        try(PreparedStatement pstmt = conn.prepareStatement(sqlQuery)) {
            pstmt.setString(1, idOrder);

            ResultSet rs = pstmt.executeQuery();

            List<GetOrderItemDto> getOrderItemDtoList = new ArrayList<>();
            while(rs.next()){
                GetOrderItemDto orderItem = new GetOrderItemDto();
                orderItem.setNoProduct(rs.getInt("NO_PRODUCT"));
                orderItem.setQtUnitPrice(rs.getInt("QT_UNIT_PRICE"));
                orderItem.setQtOrderItem(rs.getInt("QT_ORDER_ITEM"));
                orderItem.setQtOrderItemAmount(rs.getInt("QT_ORDER_ITEM_AMOUNT"));
                orderItem.setQtOrderItemDelivery(rs.getInt("QT_ORDER_ITEM_DELIVERY_FEE"));
                orderItem.setNmProduct(rs.getString("NM_PRODUCT"));
                orderItem.setIdFile(rs.getString("ID_FILE"));


                getOrderItemDtoList.add(orderItem);
            }
            return getOrderItemDtoList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public List<GetOrderItemDto> getOrderItemListDetail(String idOrder) throws SQLException {
        String sqlQuery = "select o.*,p.nm_product,p.qt_customer,p.qt_delivery_fee,p.id_file from tb_order_item o\n" +
                "join tb_product p on o.no_product = p.no_product\n" +
                "where o.id_order = ? order by cn_order_item asc";

        try(PreparedStatement pstmt = conn.prepareStatement(sqlQuery)) {
            pstmt.setString(1, idOrder);

            ResultSet rs = pstmt.executeQuery();

            List<GetOrderItemDto> getOrderItemDtoList = new ArrayList<>();
            while(rs.next()){
                GetOrderItemDto orderItem = new GetOrderItemDto();
                orderItem.setNoProduct(rs.getInt("NO_PRODUCT"));
                orderItem.setQtUnitPrice(rs.getInt("QT_UNIT_PRICE"));
                orderItem.setQtOrderItem(rs.getInt("QT_ORDER_ITEM"));
                orderItem.setQtOrderItemAmount(rs.getInt("QT_ORDER_ITEM_AMOUNT"));
                orderItem.setQtOrderItemDelivery(rs.getInt("QT_ORDER_ITEM_DELIVERY_FEE"));
                orderItem.setNmProduct(rs.getString("NM_PRODUCT"));
                orderItem.setIdFile(rs.getString("ID_FILE"));

                orderItem.setStPayment(rs.getString("ST_PAYMENT"));
                orderItem.setNoRegister(rs.getString("NO_REGISTER"));
                orderItem.setDaFirstDate(rs.getDate("DA_FIRST_DATE").toLocalDate());
                orderItem.setStPayment(rs.getString("ID_ORDER_ITEM"));
                orderItem.setIdOrder(rs.getString("ID_ORDER"));
                orderItem.setCnOrderItem(rs.getInt("CN_ORDER_ITEM"));


                getOrderItemDtoList.add(orderItem);
            }
            return getOrderItemDtoList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
