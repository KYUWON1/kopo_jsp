package com.kopo.web_final.category.controller;

import com.kopo.web_final.category.dao.CategoryDao;
import com.kopo.web_final.category.model.Category;
import com.kopo.web_final.utils.Db;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serial;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "CategoryListController", value = "/admin/category")
public class CategoryListController extends HttpServlet {
    public CategoryListController() {
        super();
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        req.setCharacterEncoding("UTF-8");

        try(Connection conn = Db.getConnection()) {
            CategoryDao dao = new CategoryDao(conn);
            List<Category> categoryList = dao.getCategoryList();

            req.setAttribute("categoryList", categoryList);
            req.getRequestDispatcher("/admin/category.jsp").forward(req, res);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
