package com.kopo.web_final.product.controller.member;

import com.kopo.web_final.category.dao.CategoryDao;
import com.kopo.web_final.category.model.Category;
import com.kopo.web_final.product.dao.ProductDao;
import com.kopo.web_final.product.dto.ProductDisplayDto;
import com.kopo.web_final.utils.Db;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serial;
import java.sql.Connection;
import java.util.List;

@WebServlet(name = "GetProductListController", value = "/main")
public class GetProductListController extends HttpServlet {
    public GetProductListController() {
        super();
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        req.setCharacterEncoding("UTF-8");
        String categoryId = req.getParameter("categoryId");


        try(Connection conn = Db.getConnection()) {
            ProductDao productDao = new ProductDao(conn);
            List<ProductDisplayDto> productListWithCategory;
            //카테고리 없을때
            if(categoryId == null || categoryId.isEmpty()){
                productListWithCategory = productDao.getAllProductListWithCategory();
            }
            // 있을때
            else{
                productDao.getProductListByCategoryId(categoryId);
                productListWithCategory = productDao.getProductListByCategoryId(categoryId);
            }

            CategoryDao categoryDao = new CategoryDao(conn);
            List<Category> categoryList = categoryDao.getCategoryList();


            req.setAttribute("categoryList", categoryList);
            req.setAttribute("productListWithCategory", productListWithCategory);
            req.getRequestDispatcher("/product/main_page.jsp").forward(req, res);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
