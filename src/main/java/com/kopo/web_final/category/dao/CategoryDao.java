package com.kopo.web_final.category.dao;

import com.kopo.web_final.category.model.Category;
import com.kopo.web_final.exception.MemberException;
import com.kopo.web_final.type.ErrorType;

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

    public List<Category> getCategoryList() throws MemberException {
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
            throw new MemberException(ErrorType.DB_QUERY_FAIL);
        }
    }

    public int insertCategory(Category category) throws MemberException {
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
            throw new MemberException(ErrorType.DB_QUERY_FAIL);
        }

    }

    public int updateCategory(String noCategory, Category category) throws MemberException {
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
            throw new MemberException(ErrorType.DB_QUERY_FAIL);
        }
    }

    public int updateUseStatus(String nbCategory, String ynUse) throws MemberException {
        String sql = "UPDATE TB_CATEGORY SET YN_USE = ? WHERE NB_CATEGORY = ?";

        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1,ynUse);
            pstmt.setString(2,nbCategory);

            return pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new MemberException(ErrorType.DB_QUERY_FAIL);
        }
    }

    public int deleteCategory(String nbCategory) throws SQLException, MemberException {
        String updateChild = "UPDATE TB_CATEGORY " +
                "SET NB_PARENT_CATEGORY = NULL, YN_USE = 'N' " +
                "WHERE NB_PARENT_CATEGORY = ?";
        String updateParent = "UPDATE TB_CATEGORY " +
                "SET YN_DELETE = 'Y', YN_USE = 'N' " +
                "WHERE NB_CATEGORY = ?";

        conn.setAutoCommit(false);
        try (
                PreparedStatement pstmtChild = conn.prepareStatement(updateChild);
                PreparedStatement pstmtParent = conn.prepareStatement(updateParent)
        ) {
            // 1. 자식들 먼저 업데이트
            pstmtChild.setString(1, nbCategory);
            pstmtChild.executeUpdate();

            // 2. 부모 카테고리 삭제 마킹
            pstmtParent.setString(1, nbCategory);
            pstmtParent.executeUpdate();

            conn.commit();
            return 1;
        } catch (SQLException e) {
            conn.rollback();
            throw new MemberException(ErrorType.DB_QUERY_FAIL);
        } catch (Exception e) {
            conn.rollback();
            throw new RuntimeException("카테고리 삭제 중 예외 발생", e);
        } finally {
            conn.setAutoCommit(true);
        }
    }

    private Date toSqlDate(LocalDate localDate) {
        return localDate != null ? java.sql.Date.valueOf(localDate) : null;
    }

}
