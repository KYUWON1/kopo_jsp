package com.kopo.web_final.product.controller;

import com.kopo.web_final.product.dao.CategoryProductMappingDao;
import com.kopo.web_final.product.dao.ProductDao;
import com.kopo.web_final.product.model.CategoryProductMapping;
import com.kopo.web_final.product.model.Product;
import com.kopo.web_final.utils.Db;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serial;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

@WebServlet(name = "ProductInsertController", value = "/admin/product-insert")
public class ProductInsertController extends HttpServlet {
    public ProductInsertController() {
        super();
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        req.setCharacterEncoding("UTF-8");

        // 순서값이랑 카테고리 번호 가져옴
        System.out.println(req.getParameter("cnOrder"));
        System.out.println(req.getParameter("nbCategory"));

        System.out.println(req.getParameter("qtCustomer"));

        System.out.println(req.getParameter("dtEndDate").replace("-",""));
        Product product = new Product();
        product.setNmProduct(req.getParameter("nmProduct"));
        product.setNmDetailExplain(req.getParameter("nmDetailExplain"));
        product.setIdFile("none");
        product.setDtStartDate(req.getParameter("dtStartDate").replace("-",""));
        product.setDtEndDate(req.getParameter("dtEndDate").replace("-",""));
        product.setQtCustomer(Integer.parseInt(req.getParameter("qtCustomer")));
        product.setQtSalePrice(Integer.parseInt(req.getParameter("qtSalePrice")));
        product.setQtStock(Integer.parseInt(req.getParameter("qtStock")));
        product.setQtDeliveryFee(Integer.parseInt(req.getParameter("qtDeliveryFee")));
        product.setNoRegister(req.getParameter("noRegister"));
        product.setDaFirstDate(LocalDate.now());

        CategoryProductMapping categoryProductMapping = new CategoryProductMapping();
        categoryProductMapping.setNbCategory(Integer.parseInt(req.getParameter("nbCategory")));
        categoryProductMapping.setCnOrder(Integer.parseInt(req.getParameter("cnOrder")));
        categoryProductMapping.setDaFirstDate(LocalDate.now());
        categoryProductMapping.setNoRegister(req.getParameter("noRegister"));

        Connection conn = null;

        try {
            conn = Db.getConnection();
            conn.setAutoCommit(false);

            ProductDao dao = new ProductDao(conn);
            String productId = dao.insertProduct(product);
            if (productId == null || productId.isEmpty()) {
                conn.rollback();
                res.sendRedirect("/admin/product?message=InsertFail&type=error");
                return;
            }


            CategoryProductMappingDao cpMappingDao = new CategoryProductMappingDao(conn);

            categoryProductMapping.setNoProduct(productId);
            int ctmResult = cpMappingDao.insertCpMapping(categoryProductMapping);
            if (ctmResult < 1) {
                conn.rollback();
                res.sendRedirect("/admin/product?message=InsertFail&type=error");
                return;
            }

            conn.commit();
            res.sendRedirect("/admin/product?message=" +
                    URLEncoder.encode("상품이 성공적으로 추가되었습니다.", "UTF-8") +
                    "&type=success");
        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (Exception rollbackEx) {
                    rollbackEx.printStackTrace(); // 로그 기록
                }
            }
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close(); // 연결 반환
                } catch (Exception closeEx) {
                    closeEx.printStackTrace(); // 로그 기록
                }
            }
        }
    }
}
