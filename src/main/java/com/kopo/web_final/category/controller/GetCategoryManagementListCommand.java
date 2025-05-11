package com.kopo.web_final.category.controller;

import com.kopo.web_final.Command;
import com.kopo.web_final.category.dao.CategoryDao;
import com.kopo.web_final.category.model.Category;
import com.kopo.web_final.utils.Db;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.util.List;

public class GetCategoryManagementListCommand implements Command {
    public GetCategoryManagementListCommand() {
        super();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        req.setCharacterEncoding("UTF-8");

        try(Connection conn = Db.getConnection()) {
            CategoryDao dao = new CategoryDao(conn);
            List<Category> categoryList = dao.getCategoryList();

            req.setAttribute("categoryList", categoryList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "/admin/category_management.jsp";
    }
}
