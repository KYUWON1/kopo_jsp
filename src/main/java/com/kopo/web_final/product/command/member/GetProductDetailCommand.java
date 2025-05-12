package com.kopo.web_final.product.command.member;

import com.kopo.web_final.Command;
import com.kopo.web_final.product.dao.ProductDao;
import com.kopo.web_final.product.model.Product;
import com.kopo.web_final.utils.Db;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.Connection;

public class GetProductDetailCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        req.setCharacterEncoding("UTF-8");
        String productId = req.getParameter("productId");

        System.out.println("GET:ProductDetail : productId : " + productId);

        try(Connection conn = Db.getConnection()){
            ProductDao dao = new ProductDao(conn);
            Product productDetailById = dao.getProductDetailById(productId);

            req.setAttribute("productDetailById", productDetailById);
        }catch (Exception e) {
            e.printStackTrace();
            return "/500.do";
        }
        return "/product/product_detail.jsp";
    }
}
