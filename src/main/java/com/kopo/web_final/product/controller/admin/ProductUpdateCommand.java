package com.kopo.web_final.product.controller.admin;

import com.kopo.web_final.Command;
import com.kopo.web_final.product.dao.CategoryProductMappingDao;
import com.kopo.web_final.product.dao.ProductDao;
import com.kopo.web_final.product.model.Product;
import com.kopo.web_final.utils.Db;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.SQLException;

public class ProductUpdateCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        req.setCharacterEncoding("UTF-8");

        String productId = req.getParameter("noProduct");
        int categoryID = Integer.parseInt(req.getParameter("nbCategory"));

        Product product = new Product();
        product.setNmProduct(req.getParameter("nmProduct"));
        product.setNmDetailExplain(req.getParameter("nmDetailExplain"));
        product.setIdFile("none");
        product.setDtStartDate(req.getParameter("dtStartDate").replace("-", ""));
        product.setDtEndDate(req.getParameter("dtEndDate").replace("-", ""));
        product.setQtCustomer(Integer.parseInt(req.getParameter("qtCustomer")));
        product.setQtSalePrice(Integer.parseInt(req.getParameter("qtSalePrice")));
        product.setQtStock(Integer.parseInt(req.getParameter("qtStock")));
        product.setQtDeliveryFee(Integer.parseInt(req.getParameter("qtDeliveryFee")));

        try (Connection conn = Db.getConnection()) {
            conn.setAutoCommit(false);

            ProductDao dao = new ProductDao(conn);
            CategoryProductMappingDao cpmDao = new CategoryProductMappingDao(conn);

            int result1 = dao.updateProduct(productId, product);
            if (result1 < 1) {
                conn.rollback();
                req.setAttribute("message", "상품 정보 수정에 실패했습니다.");
                req.setAttribute("type", "error");
                return "productManagement.do";
            }

            int result2 = cpmDao.updateCpMapping(productId, categoryID);
            if (result2 < 1) {
                conn.rollback();
                req.setAttribute("message", "카테고리 정보 수정에 실패했습니다.");
                req.setAttribute("type", "error");
                return "productManagement.do";
            }

            conn.commit();
            req.setAttribute("message", "상품이 성공적으로 업데이트되었습니다.");
            req.setAttribute("type", "success");

        } catch (Exception e) {
            req.setAttribute("message", "오류 발생: " + e.getMessage());
            req.setAttribute("type", "error");
            e.printStackTrace();
        }

        return "productManagement.do";
    }
}
