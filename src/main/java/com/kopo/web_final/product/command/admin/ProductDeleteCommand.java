package com.kopo.web_final.product.command.admin;

import com.kopo.web_final.Command;
import com.kopo.web_final.product.dao.CategoryProductMappingDao;
import com.kopo.web_final.product.dao.ProductDao;
import com.kopo.web_final.utils.Db;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.SQLException;

public class ProductDeleteCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        req.setCharacterEncoding("UTF-8");

        String noProduct = req.getParameter("noProduct");
        String nmCategory = req.getParameter("nmCategory");

        Connection conn = null;

        try {
            conn = Db.getConnection();
            conn.setAutoCommit(false);

            CategoryProductMappingDao cpmDao = new CategoryProductMappingDao(conn);
            int result1 = cpmDao.deleteCpMapping(noProduct);
            if (result1 < 1) {
                conn.rollback();
                req.setAttribute("message", "카테고리 매핑 삭제에 실패했습니다.");
                req.setAttribute("type", "error");
                return "productManagement.do";
            }

            ProductDao dao = new ProductDao(conn);
            int result2 = dao.deleteProduct(noProduct);
            if (result2 < 1) {
                conn.rollback();
                req.setAttribute("message", "상품 삭제에 실패했습니다.");
                req.setAttribute("type", "error");
                return "productManagement.do";
            }

            conn.commit();
            req.setAttribute("message", "상품이 성공적으로 삭제되었습니다.");
            req.setAttribute("type", "success");

        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            req.setAttribute("message", "오류 발생: " + e.getMessage());
            req.setAttribute("type", "error");
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return "productManagement.do";
    }
}
