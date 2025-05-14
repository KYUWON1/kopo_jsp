package com.kopo.web_final.product.dao;

import com.kopo.web_final.exception.MemberException;
import com.kopo.web_final.product.model.CategoryProductMapping;
import com.kopo.web_final.type.ErrorType;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class CategoryProductMappingDao {
    private Connection conn;

    public CategoryProductMappingDao(Connection conn) {
        this.conn = conn;
    }

    public int insertCpMapping(CategoryProductMapping categoryProductMapping) throws SQLException {
        String insertSql = "INSERT INTO tb_category_product_mapping (nb_category,no_product,cn_order,no_register,da_first_date) " +
                "VALUES (?,?,?,?,?)";
        try(PreparedStatement pstmt = conn.prepareStatement(insertSql)){
            pstmt.setObject(1, categoryProductMapping.getNbCategory(), java.sql.Types.INTEGER);
            pstmt.setObject(2, categoryProductMapping.getNoProduct(), java.sql.Types.INTEGER);
            pstmt.setInt(3, categoryProductMapping.getCnOrder());
            pstmt.setString(4, categoryProductMapping.getNoRegister());
            pstmt.setDate(5, toSqlDate(categoryProductMapping.getDaFirstDate()));

            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // 로깅만 하고
            throw e; // 예외 그대로 던짐
        }
    }

    public int updateCpMapping(String productId, int categoryID) throws SQLException {
        String updateSql = "UPDATE TB_CATEGORY_PRODUCT_MAPPING SET NB_CATEGORY = ? WHERE NO_PRODUCT = ?";
        try(PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
            pstmt.setInt(1, categoryID);
            pstmt.setString(2, productId);

            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // 로깅만 하고
            throw e; // 예외 그대로 던짐
        }
    }

    public int deleteCpMapping(String noProduct) throws SQLException {
        String deleteSql = "DELETE FROM TB_CATEGORY_PRODUCT_MAPPING WHERE NO_PRODUCT = ?";
        try(PreparedStatement pstmt = conn.prepareStatement(deleteSql)) {
            pstmt.setString(1, noProduct);

            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // 로깅만 하고
            throw e; // 예외 그대로 던짐
        }
    }

    private Date toSqlDate(LocalDate localDate) {
        return localDate != null ? java.sql.Date.valueOf(localDate) : null;
    }



}
