package com.kopo.web_final.product.dao;

import com.kopo.web_final.exception.MemberException;
import com.kopo.web_final.product.dto.ProductDisplayDto;
import com.kopo.web_final.product.model.Product;
import com.kopo.web_final.type.ErrorType;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    private Connection conn;

    public ProductDao(Connection conn) {
        this.conn = conn;
    }

    public List<ProductDisplayDto> getAllProductListWithCategory() throws MemberException {
        String sql = "SELECT\n" +
                "    p.NO_PRODUCT AS NO_PRODUCT,\n" +
                "    p.NM_PRODUCT AS NM_PRODUCT,\n" +
                "    p.NM_DETAIL_EXPLAIN AS NM_DETAIL_EXPLAIN,\n" +
                "    p.ID_FILE AS ID_FILE,\n" +
                "    p.DT_START_DATE AS DT_START_DATE,\n" +
                "    p.DT_END_DATE AS DT_END_DATE,\n" +
                "    p.QT_CUSTOMER AS QT_CUSTOMER,\n" +
                "    p.QT_SALE_PRICE AS QT_SALE_PRICE,\n" +
                "    p.QT_STOCK AS QT_STOCK,\n" +
                "    p.QT_DELIVERY_FEE AS QT_DELIVERY_FEE,\n" +
                "    p.NO_REGISTER AS NO_REGISTER,\n" +
                "    p.DA_FIRST_DATE AS DA_FIRST_DATE,\n" +
                "    NVL(c.NM_FULL_CATEGORY,'없음') AS CATEGORY_NAME,\n" +
                "    NVL(pc.NB_CATEGORY,0) AS CATEGORY_ID \n" +
                "FROM TB_PRODUCT p\n" +
                "LEFT JOIN TB_CATEGORY_PRODUCT_MAPPING pc ON p.NO_PRODUCT = pc.NO_PRODUCT\n" +
                "LEFT JOIN TB_CATEGORY c ON pc.NB_CATEGORY = c.NB_CATEGORY";

        List<ProductDisplayDto> productDtoList = new ArrayList<>();
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                // Product 객체 생성 (원래 메소드 활용)
                Product product = Product.BuildProduct(rs);

                // 카테고리 정보 추출
                int categoryId = rs.getInt("CATEGORY_ID");
                String categoryName = rs.getString("CATEGORY_NAME");

                // ProductDisplayDto 생성 및 추가
                ProductDisplayDto dto = new ProductDisplayDto();
                dto.setProduct(product);
                dto.setCategoryId(categoryId);
                dto.setCategoryName(categoryName);

                productDtoList.add(dto);
            }
            return productDtoList;
        } catch (SQLException e) {
            e.printStackTrace(); // 디버깅을 위해 예외 출력
            throw new MemberException(ErrorType.DB_QUERY_FAIL);
        }
    }

    public List<ProductDisplayDto> getProductListByCategoryId(String category) throws MemberException {
        String sql = "SELECT\n" +
                "    p.NO_PRODUCT AS NO_PRODUCT,\n" +
                "    p.NM_PRODUCT AS NM_PRODUCT,\n" +
                "    p.NM_DETAIL_EXPLAIN AS NM_DETAIL_EXPLAIN,\n" +
                "    p.ID_FILE AS ID_FILE,\n" +
                "    p.DT_START_DATE AS DT_START_DATE,\n" +
                "    p.DT_END_DATE AS DT_END_DATE,\n" +
                "    p.QT_CUSTOMER AS QT_CUSTOMER,\n" +
                "    p.QT_SALE_PRICE AS QT_SALE_PRICE,\n" +
                "    p.QT_STOCK AS QT_STOCK,\n" +
                "    p.QT_DELIVERY_FEE AS QT_DELIVERY_FEE,\n" +
                "    p.NO_REGISTER AS NO_REGISTER,\n" +
                "    p.DA_FIRST_DATE AS DA_FIRST_DATE,\n" +
                "    NVL(c.NM_FULL_CATEGORY,'없음') AS CATEGORY_NAME,\n" +
                "    NVL(m.NB_CATEGORY,0) AS CATEGORY_ID \n" +
                "FROM tb_product p\n" +
                "JOIN tb_category_product_mapping m ON p.no_product = m.no_product\n" +
                "JOIN (\n" +
                "    SELECT nb_category,nm_full_category,cn_order FROM tb_category\n" +
                "    start with nb_category = ? \n" +
                "    connect by prior nb_category = nb_parent_category\n" +
                ") c ON m.nb_category = c.nb_category" +
                " ORDER BY c.cn_order, c.nm_full_category";

        List<ProductDisplayDto> productDtoList = new ArrayList<>();
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, Integer.parseInt(category));

            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                // Product 객체 생성 (원래 메소드 활용)
                Product product = Product.BuildProduct(rs);

                // 카테고리 정보 추출
                int categoryId = rs.getInt("CATEGORY_ID");
                String categoryName = rs.getString("CATEGORY_NAME");

                // ProductDisplayDto 생성 및 추가
                ProductDisplayDto dto = new ProductDisplayDto();
                dto.setProduct(product);
                dto.setCategoryId(categoryId);
                dto.setCategoryName(categoryName);

                productDtoList.add(dto);
            }
            return productDtoList;
        } catch (SQLException e) {
            e.printStackTrace(); // 디버깅을 위해 예외 출력
            throw new MemberException(ErrorType.DB_QUERY_FAIL);
        }
    }


    public String insertProduct(Product product) throws MemberException {
        String sequenceVal = null;
        try(Statement stmt = conn.createStatement()){
            ResultSet rs = stmt.executeQuery("SELECT SEQ_TB_PRODUCT_NO.NEXTVAL FROM DUAL");
            if(rs.next()){
                sequenceVal = rs.getString(1);
            } else {
                throw new MemberException(ErrorType.DB_QUERY_FAIL);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new MemberException(ErrorType.DB_QUERY_FAIL);
        }

        String insertSql = "INSERT INTO TB_PRODUCT (" +
                "NO_PRODUCT, NM_PRODUCT, NM_DETAIL_EXPLAIN, ID_FILE, DT_START_DATE, " +
                "DT_END_DATE, QT_CUSTOMER, QT_SALE_PRICE, QT_STOCK, QT_DELIVERY_FEE, " +
                "NO_REGISTER, DA_FIRST_DATE" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

        try(PreparedStatement pstmt = conn.prepareStatement(insertSql)){
            pstmt.setString(1, sequenceVal);
            pstmt.setString(2, product.getNmProduct());
            pstmt.setString(3, product.getNmDetailExplain());
            pstmt.setString(4, product.getIdFile());
            pstmt.setString(5, product.getDtStartDate());
            pstmt.setString(6, product.getDtEndDate());
            pstmt.setInt(7, product.getQtCustomer());
            pstmt.setInt(8, product.getQtSalePrice());
            pstmt.setInt(9, product.getQtStock());
            pstmt.setInt(10, product.getQtDeliveryFee());
            pstmt.setString(11, product.getNoRegister());
            pstmt.setDate(12, toSqlDate(product.getDaFirstDate()));

            int result = pstmt.executeUpdate();
            if(result > 0) {
                return sequenceVal;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new MemberException(ErrorType.DB_QUERY_FAIL);
        }
    }

    public int updateProduct(String productId, Product product) throws MemberException {
        String updateSql = "UPDATE TB_PRODUCT SET " +
                "NM_PRODUCT = ?, " +
                "NM_DETAIL_EXPLAIN = ?, " +
                "ID_FILE = ?, " +
                "DT_START_DATE = ?, " +
                "DT_END_DATE = ?, " +
                "QT_CUSTOMER = ?, " +
                "QT_SALE_PRICE = ?, " +
                "QT_STOCK = ?, " +
                "QT_DELIVERY_FEE = ? " +
                "WHERE NO_PRODUCT = ?";  // 조건절 필수

        try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
            pstmt.setString(1, product.getNmProduct());
            pstmt.setString(2, product.getNmDetailExplain());
            pstmt.setString(3, product.getIdFile());
            pstmt.setString(4, product.getDtStartDate());
            pstmt.setString(5, product.getDtEndDate());
            pstmt.setInt(6, product.getQtCustomer());
            pstmt.setInt(7, product.getQtSalePrice());
            pstmt.setInt(8, product.getQtStock());
            pstmt.setInt(9, product.getQtDeliveryFee());
            pstmt.setString(10, productId); // WHERE절에 필요한 상품코드

            return pstmt.executeUpdate();
            // 결과 처리
        } catch (SQLException e) {
            throw new MemberException(ErrorType.DB_QUERY_FAIL);
        }
    }

    public int deleteProduct(String noProduct) throws MemberException {
        String deleteSql = "DELETE FROM TB_PRODUCT WHERE NO_PRODUCT = ?";

        try(PreparedStatement pstmt = conn.prepareStatement(deleteSql)){
            pstmt.setString(1,noProduct);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new MemberException(ErrorType.DB_QUERY_FAIL);
        }
    }

    private Date toSqlDate(LocalDate localDate) {
        return localDate != null ? java.sql.Date.valueOf(localDate) : null;
    }



}