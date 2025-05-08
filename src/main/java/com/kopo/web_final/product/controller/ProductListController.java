package com.kopo.web_final.product.controller;

import com.kopo.web_final.category.dao.CategoryDao;
import com.kopo.web_final.category.model.Category;
import com.kopo.web_final.product.dao.ProductDao;
import com.kopo.web_final.product.dto.ProductDisplayDto;
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
import java.util.List;

@WebServlet(name = "ProductListController", value = "/admin/product")
public class ProductListController extends HttpServlet {
    public ProductListController() {
        super();
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        req.setCharacterEncoding("UTF-8");

        try(Connection conn = Db.getConnection()) {
            ProductDao productDao = new ProductDao(conn);
            List<ProductDisplayDto> productListWithCategory = productDao.getProductListWithCategory();

            CategoryDao categoryDao = new CategoryDao(conn);
            List<Category> categoryList = categoryDao.getCategoryList();

            req.setAttribute("categoryList", categoryList);
            req.setAttribute("productListWithCategory", productListWithCategory);
            req.getRequestDispatcher("/admin/product.jsp").forward(req, res);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
