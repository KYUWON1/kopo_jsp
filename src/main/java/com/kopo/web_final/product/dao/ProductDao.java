package com.kopo.web_final.product.dao;

import com.kopo.web_final.basket.dto.ProductItemDto;
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

    public List<ProductDisplayDto> getAllProductListWithCategory() throws SQLException {
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
                "    NVL(pc.NB_CATEGORY,0) AS CATEGORY_ID, \n" +
                "    ct.ID_FILE AS ID_FILE \n" +
                "FROM TB_PRODUCT p\n" +
                "LEFT JOIN TB_CATEGORY_PRODUCT_MAPPING pc ON p.NO_PRODUCT = pc.NO_PRODUCT\n" +
                "LEFT JOIN TB_CATEGORY c ON pc.NB_CATEGORY = c.NB_CATEGORY\n" +
                "LEFT JOIN TB_CONTENT ct ON p.ID_FILE = ct.ID_FILE";

        List<ProductDisplayDto> productDtoList = new ArrayList<>();
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                // Product 객체 생성 (원래 메소드 활용)
                Product product = Product.BuildProduct(rs);

                // 카테고리 정보 추출
                int categoryId = rs.getInt("CATEGORY_ID");
                String categoryName = rs.getString("CATEGORY_NAME");
                String fileId = rs.getString("ID_FILE");
                // ProductDisplayDto 생성 및 추가
                ProductDisplayDto dto = new ProductDisplayDto();
                dto.setProduct(product);
                dto.setCategoryId(categoryId);
                dto.setCategoryName(categoryName);
                dto.setFileId(fileId);

                productDtoList.add(dto);
            }
            return productDtoList;
        } catch (SQLException e) {
            e.printStackTrace(); // 로깅만 하고
            throw e; // 예외 그대로 던짐
        }
    }

    public List<ProductDisplayDto> getProductListByCategoryId(String category) throws SQLException {
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
            e.printStackTrace(); // 로깅만 하고
            throw e; // 예외 그대로 던짐
        }
    }

    public List<ProductDisplayDto> getFilteredProductList(int category, String keyword, String sort) throws SQLException {
        StringBuilder sql = new StringBuilder(
                "SELECT\n" +
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
                        "    NVL(c.NM_FULL_CATEGORY, '없음') AS CATEGORY_NAME,\n" +
                        "    NVL(m.NB_CATEGORY, 0) AS CATEGORY_ID\n" +
                        "FROM TB_PRODUCT p\n" +
                        "JOIN TB_CATEGORY_PRODUCT_MAPPING m ON p.NO_PRODUCT = m.NO_PRODUCT\n" +
                        "JOIN (\n" +
                        "    SELECT NB_CATEGORY, NM_FULL_CATEGORY, CN_ORDER\n" +
                        "    FROM TB_CATEGORY\n" +
                        "    START WITH NB_CATEGORY = ? \n" +
                        "    CONNECT BY PRIOR NB_CATEGORY = NB_PARENT_CATEGORY\n" +
                        ") c ON m.NB_CATEGORY = c.NB_CATEGORY\n" +
                        "WHERE p.NM_PRODUCT LIKE ?\n"
        );

        // 동적 정렬 구문 추가
        if ("asc".equalsIgnoreCase(sort)) {
            sql.append("ORDER BY p.QT_SALE_PRICE ASC, c.CN_ORDER, c.NM_FULL_CATEGORY");
        } else if ("desc".equalsIgnoreCase(sort)) {
            sql.append("ORDER BY p.QT_SALE_PRICE DESC, c.CN_ORDER, c.NM_FULL_CATEGORY");
        } else {
            sql.append("ORDER BY c.CN_ORDER, c.NM_FULL_CATEGORY"); // 기본 정렬
        }

        List<ProductDisplayDto> productDtoList = new ArrayList<>();
        try(PreparedStatement pstmt = conn.prepareStatement(sql.toString())){
            pstmt.setInt(1, category);
            pstmt.setString(2, "%" + keyword + "%");

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
            e.printStackTrace(); // 로깅만 하고
            throw e; // 예외 그대로 던짐
        }
    }

    public Product getProductDetailById(String productId) throws SQLException {
        String sql = "SELECT * FROM TB_PRODUCT WHERE NO_PRODUCT = ?";
        try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1,productId);

            ResultSet rs = pstmt.executeQuery();
            Product product = null;
            while(rs.next()){
                product = Product.BuildProduct(rs);
            }
            return product;
        } catch (SQLException e) {
            e.printStackTrace(); // 로깅만 하고
            throw e; // 예외 그대로 던짐
        }
    }


    public String insertProduct(Product product) throws SQLException {
        String sequenceVal = null;
        try(Statement stmt = conn.createStatement()){
            ResultSet rs = stmt.executeQuery("SELECT SEQ_TB_PRODUCT_NO.NEXTVAL FROM DUAL");
            if(rs.next()){
                sequenceVal = rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // 로깅만 하고
            throw e; // 예외 그대로 던짐
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
            e.printStackTrace(); // 로깅만 하고
            throw e; // 예외 그대로 던짐
        }
    }

    public int updateProduct(String productId, Product product) throws SQLException {
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
            e.printStackTrace(); // 로깅만 하고
            throw e; // 예외 그대로 던짐
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
    public List<ProductItemDto> getProductListByBasketIdList(String[] productIds) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT * FROM TB_PRODUCT WHERE NO_PRODUCT IN (");
        for (int i = 0; i < productIds.length; i++) {
            sql.append("?");
            if (i < productIds.length - 1) sql.append(",");
        }
        sql.append(") ORDER BY DECODE(NO_PRODUCT, ");
        for (int i = 0; i < productIds.length; i++) {
            sql.append("? , ").append(i + 1);
            if (i < productIds.length - 1) sql.append(", ");
        }
        sql.append(")");

        try (PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            // 바인딩: IN절 + DECODE절
            int idx = 1;
            for (String id : productIds) {
                pstmt.setString(idx++, id);
            }
            for (String id : productIds) {
                pstmt.setString(idx++, id);
            }

            ResultSet rs = pstmt.executeQuery();
            List<ProductItemDto> productItemList = new ArrayList<>();
            while (rs.next()) {
                // Process each product
                ProductItemDto productItemDto = new ProductItemDto();
                productItemDto.setNoProduct(rs.getInt("NO_PRODUCT"));
                productItemDto.setNmProduct(rs.getString("NM_PRODUCT"));
                productItemDto.setQtCustomer(rs.getInt("QT_CUSTOMER"));
                productItemDto.setQtDeliveryFee(rs.getInt("QT_DELIVERY_FEE"));
                productItemDto.setIdFile(rs.getString("ID_FILE"));

                productItemList.add(productItemDto);
            }
            return productItemList;
        } catch (SQLException e) {
            e.printStackTrace(); // 로깅만 하고
            throw e; // 예외 그대로 던짐
        }
    }

    public int[] decreaseStocks(String[] productIds, String[] quantities) throws SQLException {
        String sql = "UPDATE tb_product SET qt_stock = qt_stock - ? WHERE no_product = ?";
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            for (int i = 0; i < productIds.length; i++) {
                int quantity = Integer.parseInt(quantities[i]);
                String productId = productIds[i];

                pstmt.setInt(1, quantity);
                pstmt.setString(2, productId);

                pstmt.addBatch(); // batch 처리
            }

            return pstmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace(); // 로깅만 하고
            throw e; // 예외 그대로 던짐
        }
    }

    public int setSoldOut(String noProduct) throws SQLException {
        String sql = "UPDATE tb_product SET QT_STOCK = 0 WHERE NO_PRODUCT = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, noProduct);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // 로깅만 하고
            throw e; // 예외 그대로 던짐
        }
    }

    public int decreaseStock(String productId, int quantity) throws SQLException {
        String sql = "UPDATE tb_product SET qt_stock = qt_stock - ? WHERE no_product = ?";
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, quantity);
            pstmt.setString(2, productId);

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