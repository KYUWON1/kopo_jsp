package com.kopo.web_final.domain.category.dao;

import com.kopo.web_final.domain.category.model.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

public class CategoryDao {
    private Connection conn;

    public CategoryDao(Connection conn) {
        this.conn = conn;
    }

    public List<Category> getCategoryList() throws SQLException {
        String sql = "SELECT\n" +
                "    NB_CATEGORY,\n" +
                "    NB_PARENT_CATEGORY,\n" +
                "    NM_CATEGORY,\n" +
                "    NM_FULL_CATEGORY,\n" +
                "    NM_EXPLAIN,\n" +
                "    CN_LEVEL,\n" +
                "    CN_ORDER,\n" +
                "    YN_USE,\n" +
                "    YN_DELETE,\n" +
                "    NO_REGISTER,\n" +
                "    DA_FIRST_DATE\n" +
                "FROM TB_CATEGORY ORDER BY CN_LEVEL, CN_ORDER";
        List<Category> categoryList = new ArrayList<>();
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                categoryList.add(Category.buildCategory(rs));
            }
            return categoryList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public int insertCategory(Category category) throws SQLException {
        String insertSql = "INSERT INTO TB_CATEGORY (\n" +
                "    NB_CATEGORY,\n" +
                "    NB_PARENT_CATEGORY,\n" +
                "    NM_CATEGORY,\n" +
                "    NM_FULL_CATEGORY,\n" +
                "    NM_EXPLAIN,\n" +
                "    CN_LEVEL,\n" +
                "    CN_ORDER,\n" +
                "    YN_USE,\n" +
                "    YN_DELETE,\n" +
                "    NO_REGISTER,\n" +
                "    DA_FIRST_DATE\n" +
                ") VALUES (\n" +
                "    SEQ_TB_CATEGORY_NO.NEXTVAL,?,?,?,?,?,?,?,?,?,? \n)";

        try(PreparedStatement pstmt = conn.prepareStatement(insertSql)){
            pstmt.setObject(1, category.getNbParentCategory(), java.sql.Types.INTEGER); // NB_PARENT_CATEGORY
            pstmt.setString(2,category.getNmCategory());
            pstmt.setString(3,category.getNmFullCategory());
            pstmt.setString(4,category.getNmExplain());
            pstmt.setInt(5,category.getCnLevel());
            pstmt.setInt(6,category.getCnOrder());
            pstmt.setString(7,category.getYnUse());
            pstmt.setString(8,category.getYnDelete());
            pstmt.setString(9,category.getNoRegister());
            pstmt.setDate(10, toSqlDate(category.getDaFirstDate()));

            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

    }

    public int updateCategory(String noCategory, Category category) throws SQLException {
        String sql = "UPDATE TB_CATEGORY SET\n" +
                "    NB_PARENT_CATEGORY = ?,\n" +
                "    NM_CATEGORY = ?,\n" +
                "    NM_FULL_CATEGORY = ?,\n" +
                "    NM_EXPLAIN = ?,\n" +
                "    CN_LEVEL = ?,\n" +
                "    CN_ORDER = ?,\n" +
                "    YN_USE = ? \n" +
                "WHERE NB_CATEGORY = ?";
        try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setObject(1, category.getNbParentCategory(), java.sql.Types.INTEGER);
            pstmt.setString(2,category.getNmCategory());
            pstmt.setString(3,category.getNmFullCategory());
            pstmt.setString(4,category.getNmExplain());
            pstmt.setInt(5,category.getCnLevel());
            pstmt.setInt(6,category.getCnOrder());
            pstmt.setString(7,category.getYnUse());
            pstmt.setString(8,noCategory);

            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public int updateUseStatusActive(String nbCategory, String ynUse) throws SQLException {
        String sql = "UPDATE TB_CATEGORY SET YN_USE = ? WHERE NB_CATEGORY = ?";

        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1,ynUse);
            pstmt.setString(2,nbCategory);

            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public int updateUseStatusInActive(String nbCategory, String ynUse) throws SQLException {
        String sql = "UPDATE TB_CATEGORY set YN_USE = ? \n" +
                "WHERE NB_CATEGORY = ? OR NB_PARENT_CATEGORY IN (\n" +
                "    SELECT NB_CATEGORY FROM TB_CATEGORY \n" +
                "    START WITH NB_CATEGORY = ? \n" +
                "    CONNECT BY PRIOR NB_CATEGORY = NB_PARENT_CATEGORY\n" +
                ")";

        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1,ynUse);
            pstmt.setString(2,nbCategory);
            pstmt.setString(3,nbCategory);

            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public int updateDeleteAndUseStatusFlush(String nbCategory) throws SQLException {
        String updateSql = "UPDATE TB_CATEGORY\n" +
                "SET \n" +
                "    YN_USE = 'N',\n" +
                "    YN_DELETE = CASE \n" +
                "        WHEN NB_CATEGORY = ? THEN 'Y' \n" +
                "        ELSE YN_DELETE \n" +
                "    END\n" +
                "WHERE NB_CATEGORY IN (\n" +
                "    SELECT NB_CATEGORY \n" +
                "    FROM TB_CATEGORY \n" +
                "    START WITH NB_CATEGORY = ? \n" +
                "    CONNECT BY PRIOR NB_CATEGORY = NB_PARENT_CATEGORY\n" +
                ")";

        int categoryId = Integer.parseInt(nbCategory);

        try (
                PreparedStatement pstmt = conn.prepareStatement(updateSql);
        ) {
            pstmt.setInt(1, categoryId);
            pstmt.setInt(2, categoryId);

            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private Date toSqlDate(LocalDate localDate) {
        return localDate != null ? java.sql.Date.valueOf(localDate) : null;
    }

}
