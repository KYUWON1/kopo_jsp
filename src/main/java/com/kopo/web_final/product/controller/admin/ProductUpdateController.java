package com.kopo.web_final.product.controller.admin;

import com.kopo.web_final.Command;
import com.kopo.web_final.product.dao.CategoryProductMappingDao;
import com.kopo.web_final.product.dao.ProductDao;
import com.kopo.web_final.product.model.Product;
import com.kopo.web_final.utils.Db;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "ProductUpdateController", value = "/admin/product-update")
public class ProductUpdateController implements Command {
    public ProductUpdateController() {
        super();
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        req.setCharacterEncoding("UTF-8");

        String productId = req.getParameter("noProduct");
        System.out.println(productId);
        // 카테고리 번호
        int categoryID = Integer.parseInt(req.getParameter("nbCategory"));

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

        Connection conn = null;
        try {
            conn = Db.getConnection();
            conn.setAutoCommit(false);

            ProductDao dao = new ProductDao(conn);
            CategoryProductMappingDao cpmDao = new CategoryProductMappingDao(conn);

            int result1 = dao.updateProduct(productId, product);
            if (result1 < 1) {
                conn.rollback();
                res.sendRedirect("/admin/product?message=UpdateFail&type=error");
                return;
            }

            int result2 = cpmDao.updateCpMapping(productId, categoryID);
            if (result2 < 1) {
                conn.rollback();
                res.sendRedirect("/admin/product?message=UpdateFail&type=error");
                return;
            }

            conn.commit();
            res.sendRedirect("/admin/product?message=" +
                    URLEncoder.encode("상품이 성공적으로 업데이트되었습니다.", "UTF-8") +
                    "&type=success");
        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "";
    }
}
