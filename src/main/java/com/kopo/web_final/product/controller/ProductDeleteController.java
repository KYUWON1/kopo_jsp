package com.kopo.web_final.product.controller;

import com.kopo.web_final.product.dao.CategoryProductMappingDao;
import com.kopo.web_final.product.dao.ProductDao;
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

@WebServlet(name = "ProductDeleteController", value = "/admin/product-delete")
public class ProductDeleteController extends HttpServlet {
    public ProductDeleteController() {
        super();
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        req.setCharacterEncoding("UTF-8");

        String noProduct = req.getParameter("noProduct");
        String nmCategory = req.getParameter("nmCategory");
        System.out.println(noProduct);
        System.out.println(nmCategory);

        Connection conn = null;
        try{
            conn = Db.getConnection();
            conn.setAutoCommit(false);
            // 매핑 테이블 삭제 => 무결성 제약 조건.  매핑된게 없으면?
            CategoryProductMappingDao cpmDao = new CategoryProductMappingDao(conn);
            if(!nmCategory.equals("0")){
                int result1 = cpmDao.deleteCpMapping(noProduct);
                if(result1 < 1){
                    res.sendRedirect("/admin/product?message=DeleteFail&type=error");
                    conn.rollback();
                    return;
                }
            }
            
            // 상품 삭제
            ProductDao dao = new ProductDao(conn);
            int result2 = dao.deleteProduct(noProduct);
            if(result2 < 1){
                res.sendRedirect("/admin/product?message=DeleteFail&type=error");
                conn.rollback();
                return;
            }


            // 성공 메시지와 함께 리다이렉트
            conn.commit();
            res.sendRedirect("/admin/product?message=" +
                    URLEncoder.encode("상품이 성공적으로 삭제되었습니다.", "UTF-8") +
                    "&type=success");
        }catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
